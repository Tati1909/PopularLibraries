package com.example.popularlibraries.view.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.popularlibraries.databinding.ItemDetailsBinding
import com.example.popularlibraries.model.entity.GitHubUserRepoEntity

class UserReposAdapter(private val delegate: Delegate) :
    ListAdapter<GitHubUserRepoEntity, UserReposAdapter.UserReposViewHolder>(DiffCallback) {

    interface Delegate {
        /**
         * Событие наступает при выборе
         * пользователя из списка
         */
        fun onItemClicked(gitHubUserRepoEntity: GitHubUserRepoEntity)
    }

    inner class UserReposViewHolder(private val binding: ItemDetailsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(gitHubUserRepoEntity: GitHubUserRepoEntity, delegate: Delegate?) {

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
                delegate.onItemClicked(getItem(position))
            }
        }
    }

    override fun onBindViewHolder(holder: UserReposViewHolder, position: Int) {
        holder.bind(getItem(position), delegate)
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