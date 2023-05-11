package org.android.go.sopt.presentation.main.search

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import org.android.go.sopt.databinding.ItemKakaoResultBinding
import org.android.go.sopt.domain.entity.kakao.KakaoSearchWebEntity

class KakaoSearchResultAdapter: ListAdapter<KakaoSearchWebEntity.Document, KakaoSearchResultAdapter.KakaoSearchResultViewHolder>(diffUtil) {

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
        return KakaoSearchResultViewHolder(ItemKakaoResultBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: KakaoSearchResultViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class KakaoSearchResultViewHolder(private val binding:ItemKakaoResultBinding):ViewHolder(binding.root) {
        fun bind(item:KakaoSearchWebEntity.Document) {
            with(binding) {
                tvTitle.text = Html.fromHtml(item.title, Html.FROM_HTML_MODE_LEGACY)
                tvContents.text = Html.fromHtml(item.contents, Html.FROM_HTML_MODE_LEGACY)
            }
        }
    }
}