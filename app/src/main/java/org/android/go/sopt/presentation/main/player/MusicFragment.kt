package org.android.go.sopt.presentation.main.player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.android.go.sopt.R
import org.android.go.sopt.databinding.FragmentMusicBinding
import org.android.go.sopt.presentation.main.OnReselectListener
import org.android.go.sopt.presentation.main.player.dialog.AddDialog
import org.android.go.sopt.presentation.main.player.music.MusicListAdapter
import org.android.go.sopt.presentation.main.player.music.MyItemDetailsLookup
import org.android.go.sopt.presentation.main.player.music.SelectionKeyProvider
import org.android.go.sopt.presentation.model.MusicItem

@AndroidEntryPoint
class MusicFragment : Fragment() {
    companion object {
        const val ADD_DIALOG = "ADD_DIALOG"
    }

    private var _binding: FragmentMusicBinding? = null
    private val binding get() = _binding!!

    private var tracker: SelectionTracker<Long>? = null

    private var musicListAdapter: MusicListAdapter? = null

    private val viewModel by viewModels<MusicViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMusicBinding.inflate(inflater, container, false)
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
        viewModel.musicState.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED).onEach {
            when (it) {
                is MusicState.UnInitialized -> {
                    initAddButton()
                    initAdapter()
                    initRecyclerView()
                    viewModel.getMusicList()
                }

                is MusicState.SuccessMusicList -> {
                    successHandler(it.musicList)
                }

                else -> {
                    //TODO 다음주에 계속...
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
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