package org.android.go.sopt.presentation.main.home.reqres

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import org.android.go.sopt.data.model.reqres.ReqresResponse
import org.android.go.sopt.databinding.ItemReqesBinding

class ReqresAdapter : ListAdapter<ReqresResponse.Data, ReqresAdapter.ReqresViewHolder>(diffUtil) {

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<ReqresResponse.Data>() {
            override fun areItemsTheSame(oldItem: ReqresResponse.Data, newItem: ReqresResponse.Data): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ReqresResponse.Data, newItem: ReqresResponse.Data): Boolean {
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
        fun bind(item: ReqresResponse.Data) {
            with(binding) {
                tvUserName.text = String.format("%s %s", item.firstName, item.lastName)
                tvUserEmail.text = item.email
                ivUserProfile.load(item.avatar)
            }
        }
    }
}