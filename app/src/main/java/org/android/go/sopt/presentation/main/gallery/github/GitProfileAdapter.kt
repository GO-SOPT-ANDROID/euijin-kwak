package org.android.go.sopt.presentation.main.gallery.github

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import coil.transform.CircleCropTransformation
import org.android.go.sopt.databinding.ItemGitProfileBinding

class GitProfileAdapter(private val userData: Pair<String, String>?) : RecyclerView.Adapter<GitProfileAdapter.GirProfileViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GirProfileViewHolder {
        return GirProfileViewHolder(ItemGitProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: GirProfileViewHolder, position: Int) {
        if (userData != null) {
            holder.bind(userData)
        }
    }

    class GirProfileViewHolder(private val binding: ItemGitProfileBinding) : ViewHolder(binding.root) {
        fun bind(userData: Pair<String, String>) {
            with(binding) {
                ivProfile.load(userData.first) {
                    transformations(CircleCropTransformation())
                }
                binding.tvName.text = userData.second
            }
        }
    }
}