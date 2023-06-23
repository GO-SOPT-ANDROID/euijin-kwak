package org.android.go.sopt.presentation.main.home.reqres

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import org.android.go.sopt.databinding.ItemReqesBinding
import org.android.go.sopt.domain.entity.reqres.ReqresEntity

class ReqresAdapter : ListAdapter<ReqresEntity.Data, ReqresAdapter.ReqresViewHolder>(diffUtil) {

    // Companion Object는 클래스 내부에서 하단에 선언하는 것이 코틀린 스타일에서 권장하는 방법입니다.
    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<ReqresEntity.Data>() {
            override fun areItemsTheSame(oldItem: ReqresEntity.Data, newItem: ReqresEntity.Data): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ReqresEntity.Data, newItem: ReqresEntity.Data): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReqresViewHolder {
        return ReqresViewHolder(ItemReqesBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ReqresViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class ReqresViewHolder(private val binding: ItemReqesBinding) : ViewHolder(binding.root) {
        fun bind(item: ReqresEntity.Data) {
            with(binding) {
                tvUserName.text = String.format("%s %s", item.firstName, item.lastName)
                tvUserEmail.text = item.email
                ivUserProfile.load(item.avatar)
            }
        }
    }
}