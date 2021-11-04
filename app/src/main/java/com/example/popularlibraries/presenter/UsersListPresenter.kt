package com.example.popularlibraries.presenter

import com.example.popularlibraries.model.GithubUser
import com.example.popularlibraries.view.users.UsersRVAdapter

//Presenter с данными пользователей(GithubUser) и общими методами обработки списка
class UsersListPresenter : IUserListPresenter {

    val users = mutableListOf<GithubUser>()

    override var itemClickListener: ((UsersRVAdapter.UserItemView) -> Unit)? = null

    override fun getCount() = users.size

    override fun bindView(view: UsersRVAdapter.UserItemView) {
        val user = users[view.pos]
        view.setLogin(user.login)
    }
}