package com.example.popularlibraries.presenter

import com.example.popularlibraries.model.GithubUser
import com.example.popularlibraries.model.GithubUsersRepo
import com.example.popularlibraries.view.UserItemView
import com.example.popularlibraries.view.UsersView
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter

//Необходим для навигации
class UsersPresenter(val usersRepo: GithubUsersRepo, val router: Router) :
    MvpPresenter<UsersView>() {
    class UsersListPresenter : IUserListPresenter {
        val users = mutableListOf<GithubUser>()
        override var itemClickListener: ((UserItemView) -> Unit)? = null
        override fun getCount() = users.size
        override fun bindView(view: UserItemView) {
            val user = users[view.pos]
            view.setLogin(user.login)
        }
    }

    val usersListPresenter = UsersListPresenter()
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadData()
        usersListPresenter.itemClickListener = { itemView ->
//TODO: переход на экран пользователя c помощью router.navigateTo
        }
    }

    fun loadData() {
        val users = usersRepo.getUsers()
        usersListPresenter.users.addAll(users)
        viewState.updateList()
    }

    ////Для обработки нажатия клавиши «Назад» добавляем функцию backPressed(). Она возвращает
    //    //Boolean, где мы передаём обработку выхода с экрана роутеру. Вообще, функции Presenter, согласно
    //    //парадигме, не должны ничего возвращать, но в нашем случае приходится идти на компромисс из-за
    //    //недостатков фреймворка.
    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}