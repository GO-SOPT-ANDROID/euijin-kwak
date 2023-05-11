package org.android.go.sopt.presentation.main.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import org.android.go.sopt.databinding.ItemKakaoResultBinding
import org.android.go.sopt.domain.entity.kakao.KakaoSearchWebEntity
import org.android.go.sopt.extension.fromHtmlLegacy

class KakaoSearchResultAdapter(private val onClickItem: (String) -> Unit) :
    ListAdapter<KakaoSearchWebEntity.Document, KakaoSearchResultAdapter.KakaoSearchResultViewHolder>(diffUtil) {

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<KakaoSearchWebEntity.Document>() {
            override fun areItemsTheSame(oldItem: KakaoSearchWebEntity.Document, newItem: KakaoSearchWebEntity.Document): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: KakaoSearchWebEntity.Document, newItem: KakaoSearchWebEntity.Document): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KakaoSearchResultViewHolder {
        return KakaoSearchResultViewHolder(
            ItemKakaoResultBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onClickItem
        )
    }

    override fun onBindViewHolder(holder: KakaoSearchResultViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class KakaoSearchResultViewHolder(
        private val binding: ItemKakaoResultBinding,
        private val onClickItem: (String) -> Unit
    ) : ViewHolder(binding.root) {
        fun bind(item: KakaoSearchWebEntity.Document) {
            with(binding) {
                tvTitle.text = item.title.fromHtmlLegacy()
                tvContents.text = item.contents.fromHtmlLegacy()
            }
            itemView.setOnClickListener {
                onClickItem(item.url)
            }
        }
    }
}