package org.android.go.sopt.presentation.main

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

    var tracker: SelectionTracker<Long>? = null

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
        holder.bind(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    inner class MainViewHolder(private val binding: ItemMusicBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            with(binding) {
                tvMusicName.text = currentList[position].musicName
                tvSingerName.text = currentList[position].singerName
                ivMusicImage.load(currentList[position].imageUrl)
                itemView.isActivated = tracker?.isSelected(position.toLong()) ?: false
            }

            binding.root.setOnClickListener {
                tracker?.select(position.toLong())
            }
        }

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
            object : ItemDetailsLookup.ItemDetails<Long>() {
                override fun getPosition(): Int = adapterPosition
                override fun getSelectionKey(): Long = position.toLong()
            }
    }
}
