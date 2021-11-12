package com.example.popularlibraries.presenter

import com.example.popularlibraries.model.GithubUserRepository
import com.example.popularlibraries.view.details.DetailsView
import moxy.MvpPresenter

class DetailPresenter(
    private val userLogin: String,
    private val gitHubRepo: GithubUserRepository
) :
    MvpPresenter<DetailsView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        /***
        //let проверка на null и вызываем метод showUser интерфейса DetailView
         */
        gitHubRepo
            .getUserByLogin(userLogin)
            ?.let(viewState::showUser)
    }
}