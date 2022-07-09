package com.example.popularlibraries.view.users

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.popularlibraries.R
import com.example.popularlibraries.databinding.ItemUserBinding
import com.example.popularlibraries.model.datasource.GithubUser
import com.example.popularlibraries.view.setStartDrawableCircleImageFromUri

//Адаптер — это фреймворковая вещь, а, значит, он — ui. Соответственно, логики и данных прямо в нём быть не должно.
//Может показаться, что если адаптер — ui, то он и есть View. Но это не так.
class UsersAdapter(
    private val onUserClicked: (GithubUser) -> Unit
) : PagingDataAdapter<GithubUser, RecyclerView.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(viewType, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            (holder as UserViewHolder).bind(item)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_user
    }

    inner class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemUserBinding.bind(view)

        init {
            binding.root.setOnClickListener {
                getItem(bindingAdapterPosition)?.let { it1 -> onUserClicked(it1) }
            }
        }

        fun bind(item: GithubUser) {
            //загружаем аватарку слева от логина пользователя
            binding.loginTextView.setStartDrawableCircleImageFromUri(item.avatarUrl)
            binding.loginTextView.text = item.login
        }
    }

    companion object {

        private val DiffCallback = object : DiffUtil.ItemCallback<GithubUser>() {
            override fun areItemsTheSame(oldItem: GithubUser, newItem: GithubUser): Boolean {
                return oldItem.login == newItem.login
            }

            override fun areContentsTheSame(oldItem: GithubUser, newItem: GithubUser): Boolean {
                return oldItem == newItem
            }
        }
    }
}