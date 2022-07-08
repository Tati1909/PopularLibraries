package com.example.popularlibraries.view.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.popularlibraries.R
import com.example.popularlibraries.databinding.ItemUsersFooterBinding

class FooterUsersAdapter() : LoadStateAdapter<FooterUsersAdapter.FooterLoadStateViewHolder>() {

    override fun onBindViewHolder(holder: FooterLoadStateViewHolder, loadState: LoadState) {
        holder.bindTo(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): FooterLoadStateViewHolder {
        return FooterLoadStateViewHolder(parent)
    }

    inner class FooterLoadStateViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_users_footer, parent, false)
    ) {

        private val binding = ItemUsersFooterBinding.bind(itemView)

        fun bindTo(loadState: LoadState) {
            binding.errorView.isVisible = loadState is LoadState.Error
            binding.progressBar.isVisible = loadState is LoadState.Loading
        }
    }

}