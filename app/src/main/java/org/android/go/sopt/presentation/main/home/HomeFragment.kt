package org.android.go.sopt.presentation.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import org.android.go.sopt.databinding.FragmentHomeBinding
import org.android.go.sopt.presentation.main.OnReselectListener
import org.android.go.sopt.presentation.main.home.music.MusicListAdapter
import org.android.go.sopt.presentation.main.home.music.MyItemDetailsLookup
import org.android.go.sopt.presentation.main.home.music.SelectionKeyProvider
import org.android.go.sopt.util.MusicList

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var tracker: SelectionTracker<Long>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initAdapter() {
        val musicListAdapter = MusicListAdapter()
        initRecyclerView(musicListAdapter)
    }

    private fun initRecyclerView(musicListAdapter: MusicListAdapter) {
        with(binding.rvMusicList) {
            adapter = musicListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        setMusicData(musicListAdapter)
        setSelectionTracker(musicListAdapter)
    }

    private fun setMusicData(musicListAdapter: MusicListAdapter) {
        musicListAdapter.submitList(MusicList.musicList)
    }

    private fun setSelectionTracker(musicListAdapter: MusicListAdapter) {
        val recyclerView = binding.rvMusicList
        tracker = SelectionTracker.Builder(
            "musicSelection",
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
                binding.btDelete.setOnClickListener {
                tracker.selection.forEach { id ->
                        val holder = binding.rvMusicList.findViewHolderForItemId(id)
                        if (holder is MusicListAdapter.MainViewHolder) {
                            val musicData = holder.element
                            MusicList.musicList.remove(musicData)
                        }
                    }
                    tracker.clearSelection()
                    binding.rvMusicList.adapter?.notifyDataSetChanged()
                }
            }
        })
    }

    fun setOnReselectListener() = OnReselectListener {
        binding.rvMusicList.scrollToPosition(0)
    }
}