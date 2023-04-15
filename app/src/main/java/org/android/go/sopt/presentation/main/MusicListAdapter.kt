package org.android.go.sopt.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import org.android.go.sopt.databinding.ItemMusicBinding
import org.android.go.sopt.model.MusicData

class MusicListAdapter: ListAdapter<MusicData, MusicListAdapter.MainViewHolder>(diffUtil) {

    companion object {
        //코틀린 컨벤션상 companion object는 클래스의 맨 아래에 위치시킵니다~~!! 절 따라하지 마세요. (개인 취향)
        private val diffUtil = object : DiffUtil.ItemCallback<MusicData>() {
            override fun areItemsTheSame(oldItem: MusicData, newItem: MusicData): Boolean {
                return oldItem.musicName == newItem.musicName
            }

            override fun areContentsTheSame(oldItem: MusicData, newItem: MusicData): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(ItemMusicBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MainViewHolder(private val binding: ItemMusicBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(musicData: MusicData) {
            with(binding) {
                tvMusicName.text = musicData.musicName
                tvSingerName.text = musicData.singerName
                ivMusicImage.load(musicData.imageUrl)
            }
        }
    }
}
