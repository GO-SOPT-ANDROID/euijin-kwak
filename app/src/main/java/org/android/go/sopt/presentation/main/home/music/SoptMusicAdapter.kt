package org.android.go.sopt.presentation.main.home.music

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import org.android.go.sopt.databinding.ItemSoptMusicBinding
import org.android.go.sopt.domain.entity.music.SoptGetMusicData

class SoptMusicAdapter : ListAdapter<SoptGetMusicData.Music, SoptMusicAdapter.MusicViewHolder>(diffUtil) {

    // Companion Object는 클래스 내부에서 하단에 선언하는 것이 코틀린 스타일에서 권장하는 방법입니다.
    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<SoptGetMusicData.Music>() {
            override fun areItemsTheSame(oldItem: SoptGetMusicData.Music, newItem: SoptGetMusicData.Music): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: SoptGetMusicData.Music, newItem: SoptGetMusicData.Music): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        return MusicViewHolder(ItemSoptMusicBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class MusicViewHolder(private val binding: ItemSoptMusicBinding) : ViewHolder(binding.root) {
        fun bind(item: SoptGetMusicData.Music) {
            with(binding) {
                tvSingerName.text = item.singer
                tvMusicTitle.text = item.title
                ivMusicProfile.load(item.url)
            }
        }
    }
}