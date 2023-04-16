package org.android.go.sopt.presentation.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.selection.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.android.go.sopt.databinding.FragmentHomeBinding
import org.android.go.sopt.presentation.main.MainActivity
import org.android.go.sopt.presentation.main.MusicListAdapter
import org.android.go.sopt.util.MusicList

class HomeFragment : Fragment(), MainActivity.OnReselectListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var tracker: SelectionTracker<Long>? = null


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

    override fun onReselect() {
        binding.rvMusicList.smoothScrollToPosition(0)
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

        tracker = SelectionTracker.Builder(
            "musicSelection",
            binding.rvMusicList,
            StableIdKeyProvider(binding.rvMusicList),
            MyItemDetailsLookup(binding.rvMusicList),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(
            SelectionPredicates.createSelectAnything()
        ).build()
        musicListAdapter.tracker = tracker
    }

    private fun setMusicData(musicListAdapter: MusicListAdapter) {
        musicListAdapter.submitList(MusicList.musicList)
    }

    class MyItemDetailsLookup(private val recyclerView: RecyclerView) : ItemDetailsLookup<Long>() {
        override fun getItemDetails(event: MotionEvent): ItemDetails<Long>? {
            val view = recyclerView.findChildViewUnder(event.x, event.y)
            if (view != null) {
                return (recyclerView.getChildViewHolder(view) as? MusicListAdapter.MainViewHolder)?.getItemDetails()
            }
            return null
        }
    }
}