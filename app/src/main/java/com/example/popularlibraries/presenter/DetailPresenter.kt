package com.example.popularlibraries.presenter

import com.example.popularlibraries.model.GithubUser
import com.example.popularlibraries.model.GithubUsersRepo
import com.example.popularlibraries.view.details.DetailsView
import moxy.MvpPresenter

class DetailPresenter(
    private val userLogin: String,
    private val gitHubRepo: GithubUsersRepo
) :
    MvpPresenter<DetailsView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        /***
        fun getUsers() возвращает List<GithubUser>
        firstOrNull возвращает элемент списка, соответствующий заданному предикату, или null, если элемент не был найден.*/
        gitHubRepo
            .getUsers()
            .firstOrNull { user: GithubUser -> user.login == userLogin }
            //let проверка на null и вызываем метод showUser интерфейса DetailView
            ?.let(viewState::showUser)
    }
}