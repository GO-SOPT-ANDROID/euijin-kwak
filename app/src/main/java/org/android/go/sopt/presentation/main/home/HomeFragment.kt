package org.android.go.sopt.presentation.main.home

import android.os.Bundle
import android.util.Log
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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.android.go.sopt.R
import org.android.go.sopt.databinding.FragmentHomeBinding
import org.android.go.sopt.presentation.main.OnReselectListener
import org.android.go.sopt.presentation.main.home.music.MusicListAdapter
import org.android.go.sopt.presentation.main.home.music.MyItemDetailsLookup
import org.android.go.sopt.presentation.main.home.music.SelectionKeyProvider

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var tracker: SelectionTracker<Long>

    private val musicListAdapter by lazy { MusicListAdapter() }

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
        _binding = null
        super.onDestroyView()
    }

    private fun initObserve() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.homeState.collectLatest {
                    when (it) {
                        is HomeState.UnInitialized -> {
                            initRecyclerView(musicListAdapter)
                            viewModel.getMusicList()
                        }


                        is HomeState.SuccessMusicList -> {
                            musicListAdapter.submitList(it.musicList)
                        }

                        else -> {

                        }
                    }
                }
            }
        }
    }

    private fun initRecyclerView(musicListAdapter: MusicListAdapter) {
        with(binding.rvMusicList) {
            adapter = musicListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        setSelectionTracker(musicListAdapter)
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
        ).build()
        musicListAdapter.setSelectionTracker(tracker)
        setTrackerObserve(tracker)
    }

    private fun setTrackerObserve(
        tracker: SelectionTracker<Long>,
    ) {
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