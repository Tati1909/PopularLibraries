package com.example.popularlibraries.presenter

import com.example.popularlibraries.model.GithubUser
import com.example.popularlibraries.model.GithubUsersRepo
import com.example.popularlibraries.view.IScreens
import com.example.popularlibraries.view.MainView
import com.example.popularlibraries.view.UserItemView
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter

//Здесь нет ничего, кроме замены экрана с пустоты, на экран пользователей во время старта и
//обработки команды «Назад».
class MainPresenter(val router: Router, val screens: IScreens) :
    MvpPresenter<MainView>() {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.replaceScreen(screens.users())
    }
    fun backClicked() {
        router.exit()
    }
}