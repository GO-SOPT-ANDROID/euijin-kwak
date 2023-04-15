package org.android.go.sopt.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.android.go.sopt.databinding.ItemMainTitleBinding

class MainTitleAdapter: RecyclerView.Adapter<MainTitleAdapter.MainTitleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainTitleViewHolder {
        return MainTitleViewHolder(ItemMainTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MainTitleViewHolder, position: Int) {}

    override fun getItemCount(): Int = 1

    class MainTitleViewHolder(binding: ItemMainTitleBinding): RecyclerView.ViewHolder(binding.root) {}
}