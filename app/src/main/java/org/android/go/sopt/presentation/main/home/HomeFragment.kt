package org.android.go.sopt.presentation.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.android.go.sopt.R
import org.android.go.sopt.databinding.FragmentHomeBinding
import org.android.go.sopt.presentation.main.OnReselectListener
import org.android.go.sopt.presentation.main.home.dialog.AddDialog
import org.android.go.sopt.presentation.main.home.music.MusicListAdapter
import org.android.go.sopt.presentation.main.home.music.MyItemDetailsLookup
import org.android.go.sopt.presentation.main.home.music.SelectionKeyProvider
import org.android.go.sopt.presentation.model.MusicItem

@AndroidEntryPoint
class HomeFragment : Fragment() {
    companion object {
        const val ADD_DIALOG = "ADD_DIALOG"
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var tracker: SelectionTracker<Long>? = null

    private var musicListAdapter: MusicListAdapter? = null

    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserve()
    }

    override fun onDestroyView() {
        musicListAdapter = null
        tracker = null
        _binding = null
        super.onDestroyView()
    }

    private fun initObserve() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.homeState.collectLatest {
                    when (it) {
                        is HomeState.UnInitialized -> {
                            initAddButton()
                            initAdapter()
                            initRecyclerView()
                            viewModel.getMusicList()
                        }

                        is HomeState.SuccessMusicList -> {
                            successHandler(it.musicList)
                        }

                        else -> {
                            //TODO 다음주에 계속...
                        }
                    }
                }
            }
        }
    }

    private fun initAdapter() {
        musicListAdapter = MusicListAdapter { currentMusicData ->
            AddDialog().apply {
                arguments?.putParcelable(AddDialog.MUSIC_DATA, currentMusicData)
                setOnItemClickListener {
                    viewModel.updateMusic(it)
                }
            }.let { parentFragmentManager.beginTransaction().add(it, ADD_DIALOG).commitAllowingStateLoss() }
        }
    }

    private fun successHandler(musicList: List<MusicItem>) {
        musicListAdapter?.submitList(musicList)
        viewLifecycleOwner.lifecycleScope.launch {
            delay(100)
            binding.rvMusicList.smoothScrollToPosition(0)
        }
    }

    private fun initAddButton() {
        binding.btAdd.setOnClickListener {
            AddDialog().apply {
                setOnItemClickListener { musicData -> viewModel.insertMusic(musicData) }
            }.let { parentFragmentManager.beginTransaction().add(it, ADD_DIALOG).commitAllowingStateLoss() }
        }
    }

    private fun initRecyclerView() {
        musicListAdapter?.let {
            with(binding.rvMusicList) {
                adapter = it
                layoutManager = LinearLayoutManager(requireContext())
            }
            setSelectionTracker(it)
        }  //TODO Null ERROR 처리 필요
    }

    private fun setSelectionTracker(musicListAdapter: MusicListAdapter) {
        val recyclerView = binding.rvMusicList
        tracker = SelectionTracker.Builder(
            getString(R.string.music_selection),
            recyclerView,
            SelectionKeyProvider(recyclerView),
            MyItemDetailsLookup(recyclerView),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(
            SelectionPredicates.createSelectAnything()
        ).build().also {
            musicListAdapter.setSelectionTracker(it)
            setTrackerObserve(it)
        }
    }

    private fun setTrackerObserve(tracker: SelectionTracker<Long>) {
        tracker.addObserver(object : SelectionTracker.SelectionObserver<Long>() {
            override fun onSelectionChanged() {
                super.onSelectionChanged()
                with(binding) {
                    btDelete.setOnClickListener {
                        tracker.selection.forEach { id ->
                            val holder = rvMusicList.findViewHolderForItemId(id)
                            if (holder is MusicListAdapter.MainViewHolder) {
                                val musicData = holder.element ?: return@forEach
                                viewModel.deleteMusic(musicData)
                            }
                        }
                        tracker.clearSelection()
                    }
                }
            }
        })
    }

    fun setOnReselectListener() = OnReselectListener {
        binding.rvMusicList.scrollToPosition(0)
    }
}