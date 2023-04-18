package org.android.go.sopt.presentation.main.home.music

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import org.android.go.sopt.databinding.ItemMusicBinding
import org.android.go.sopt.model.MusicData

class MusicListAdapter : ListAdapter<MusicData, MusicListAdapter.MainViewHolder>(diffUtil) {

    private var tracker: SelectionTracker<Long>? = null

    init {
        setHasStableIds(true)
    }

    companion object {
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
        holder.bind(currentList[position], tracker)
    }

    override fun getItemId(position: Int): Long {
        return currentList[position].id.toLong()
    }

    fun setSelectionTracker(tracker: SelectionTracker<Long>) {
        this.tracker = tracker
    }

    class MainViewHolder(private val binding: ItemMusicBinding) : RecyclerView.ViewHolder(binding.root) {
        var element:MusicData? = null
        fun bind(musicData: MusicData, tracker: SelectionTracker<Long>?) {
            element = musicData
            with(binding) {
                tvMusicName.text = musicData.musicName
                tvSingerName.text = musicData.singerName
                ivMusicImage.load(musicData.imageUrl)
                itemView.isActivated = tracker?.isSelected(itemId) ?: false
            }
        }

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
            object : ItemDetailsLookup.ItemDetails<Long>() {
                override fun getPosition(): Int = adapterPosition
                override fun getSelectionKey(): Long = itemId
            }
    }
}
