package org.android.go.sopt.presentation.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationBarView.OnItemReselectedListener
import org.android.go.sopt.databinding.FragmentHomeBinding
import org.android.go.sopt.presentation.main.MainTitleAdapter
import org.android.go.sopt.presentation.main.MusicListAdapter
import org.android.go.sopt.util.MusicList

class HomeFragment:Fragment(), OnItemReselectedListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

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

    override fun onNavigationItemReselected(item: MenuItem) {
        binding.rvMusicList.smoothScrollToPosition(0)
    }

    private fun initAdapter() {
        val mainTitleAdapter = MainTitleAdapter()
        val musicListAdapter = MusicListAdapter()
        initRecyclerView(mainTitleAdapter, musicListAdapter)
    }

    private fun initRecyclerView(mainTitleAdapter: MainTitleAdapter, musicListAdapter: MusicListAdapter) {
        with(binding.rvMusicList) {
            adapter = ConcatAdapter(mainTitleAdapter, musicListAdapter)
            layoutManager = LinearLayoutManager(requireContext())
        }
        setMusicData(musicListAdapter)
    }

    private fun setMusicData(musicListAdapter: MusicListAdapter) {
        musicListAdapter.submitList(MusicList.musicList)
    }
}