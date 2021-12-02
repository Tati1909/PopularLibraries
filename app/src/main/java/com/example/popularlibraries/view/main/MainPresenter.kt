package com.example.popularlibraries.view.main

import com.example.popularlibraries.navigation.UsersScreen
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter

class MainPresenter(private val router: Router) :
    MvpPresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        //Можно так router.replaceScreen( UsersScreen.create())
        router.newRootScreen(UsersScreen.create())
    }

    fun backClicked() {
        router.exit()
    }
}