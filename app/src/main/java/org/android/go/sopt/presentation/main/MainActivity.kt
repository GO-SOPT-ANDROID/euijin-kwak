package org.android.go.sopt.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import org.android.go.sopt.databinding.ActivityMainBinding
import org.android.go.sopt.presentation.base.BaseViewBindingActivity
import org.android.go.sopt.util.MusicList

class MainActivity : BaseViewBindingActivity<ActivityMainBinding>() {
    override fun setBinding(layoutInflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAdapter()
    }

    private fun initAdapter() {
        val mainTitleAdapter = MainTitleAdapter()
        val musicListAdapter = MusicListAdapter()
        initRecyclerView(mainTitleAdapter, musicListAdapter)
    }

    private fun initRecyclerView(mainTitleAdapter: MainTitleAdapter, musicListAdapter: MusicListAdapter) {
        with(binding.rvMusicList) {
            adapter = ConcatAdapter(mainTitleAdapter, musicListAdapter)
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
        setMusicData(musicListAdapter)
    }

    private fun setMusicData(musicListAdapter: MusicListAdapter) {
        musicListAdapter.submitList(MusicList.musicList)
    }
}