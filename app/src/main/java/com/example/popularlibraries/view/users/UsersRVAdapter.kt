package com.example.popularlibraries.view.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.popularlibraries.databinding.ItemUserBinding
import com.example.popularlibraries.model.datasource.GithubUser
import com.example.popularlibraries.view.setStartDrawableCircleImageFromUri

//Адаптер не имеет ссылок на данные и  делегирует процесс наполнения View через delegate.
//Адаптер — это фреймворковая вещь, а, значит, он —
//ui. Соответственно, логики и данных прямо в нём быть не должно.
//Может показаться, что если адаптер — ui, то он и
//есть View. Но это не так.

class UsersRVAdapter(private val delegate: Delegate) :
    ListAdapter<GithubUser, UsersRVAdapter.ViewHolder>(DiffCallback) {

    interface Delegate {
        /**
         * Событие наступает при выборе
         * пользователя из списка
         */
        fun onItemClicked(user: GithubUser)
    }

    class ViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(gitHubUser: GithubUser, delegate: Delegate?) {
            //загружаем аватарку слева от логина пользователя
            binding.loginTextView.setStartDrawableCircleImageFromUri(gitHubUser.avatarUrl)
            binding.loginTextView.text = gitHubUser.login
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemUserBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        ).apply {
            itemView.setOnClickListener {

                val position = this.adapterPosition
                delegate.onItemClicked(getItem(position))
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position), delegate)

    companion object {
        //DiffCallback класс, который мы указали для ListAdapter? Это просто объект,
        //который помогает ListAdapter определить, какие элементы в новом и старом списках отличаются при обновлении списка.
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