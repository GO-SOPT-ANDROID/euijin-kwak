package org.android.go.sopt.presentation.main.gallery.github

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import org.android.go.sopt.R
import org.android.go.sopt.databinding.ItemGitRepoBinding
import org.android.go.sopt.data.model.git.FakeRepoResponse



class GitRepoAdapter(private val onClick: (String) -> Unit) : ListAdapter<FakeRepoResponse.FakeRepoResponseItem, GitRepoAdapter.GitRepoViewHolder>(diffUtil) {
    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<FakeRepoResponse.FakeRepoResponseItem>() {
            override fun areItemsTheSame(oldItem: FakeRepoResponse.FakeRepoResponseItem, newItem: FakeRepoResponse.FakeRepoResponseItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: FakeRepoResponse.FakeRepoResponseItem, newItem: FakeRepoResponse.FakeRepoResponseItem): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitRepoViewHolder {
        return GitRepoViewHolder(ItemGitRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false), onClick)
    }

    override fun onBindViewHolder(holder: GitRepoViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class GitRepoViewHolder(private val binding: ItemGitRepoBinding, private val onClick: (String) -> Unit) : ViewHolder(binding.root) {
        fun bind(fakeRepoResponseItem: FakeRepoResponse.FakeRepoResponseItem) {
            with(binding) {
                root.context.let { context ->
                    tvRepoName.text = fakeRepoResponseItem.name
                    tvRepoDescription.text = fakeRepoResponseItem.description
                    tvOwner.text = context.getString(R.string.owner, fakeRepoResponseItem.owner.login)
                    tvRepoLanguage.text = context.getString(R.string.language, fakeRepoResponseItem.language)
                    tvRepoStar.text = context.getString(R.string.stars, fakeRepoResponseItem.stargazers_count.toString())
                    tvRepoFork.text = context.getString(R.string.forks, fakeRepoResponseItem.forks_count.toString())
                }
            }
            itemView.setOnClickListener {
                onClick(fakeRepoResponseItem.html_url)
            }
        }
    }

}
