package com.example.popularlibraries.presenter

import com.example.popularlibraries.view.IScreens
import com.example.popularlibraries.view.details.DetailsView
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter

class DetailPresenter(val router: Router, val screens: IScreens) :
    MvpPresenter<DetailsView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.replaceScreen(screens.users())
    }

    fun backClicked() {
        router.exit()
    }
}