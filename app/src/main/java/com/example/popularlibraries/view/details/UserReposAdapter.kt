package com.example.popularlibraries.view.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.popularlibraries.databinding.ItemDetailsBinding
import com.example.popularlibraries.model.entity.GitHubUserRepoEntity

class UserReposAdapter(
    private val onItemClicked: (gitHubUserRepoEntity: GitHubUserRepoEntity) -> Unit
) : ListAdapter<GitHubUserRepoEntity, UserReposAdapter.UserReposViewHolder>(DiffCallback) {

    inner class UserReposViewHolder(private val binding: ItemDetailsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(gitHubUserRepoEntity: GitHubUserRepoEntity) {

            binding.textViewItem.text = gitHubUserRepoEntity.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserReposViewHolder {
        return UserReposViewHolder(
            ItemDetailsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        ).apply {
            itemView.setOnClickListener {
                val position = this.adapterPosition
                onItemClicked(getItem(position))
            }
        }
    }

    override fun onBindViewHolder(holder: UserReposViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {

        //DiffCallback класс, который мы указали для ListAdapter? Это просто объект,
        //который помогает ListAdapter определить, какие элементы в новом и старом списках отличаются при обновлении списка.
        private val DiffCallback = object : DiffUtil.ItemCallback<GitHubUserRepoEntity>() {

            override fun areItemsTheSame(
                oldItem: GitHubUserRepoEntity,
                newItem: GitHubUserRepoEntity
            ): Boolean =
                oldItem.id == newItem.id &&
                    oldItem.name == newItem.name

            override fun areContentsTheSame(
                oldItem: GitHubUserRepoEntity,
                newItem: GitHubUserRepoEntity
            ): Boolean =
                oldItem == newItem
        }
    }
}