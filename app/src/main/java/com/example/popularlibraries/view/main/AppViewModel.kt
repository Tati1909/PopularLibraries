package com.example.popularlibraries.view.main

import com.example.popularlibraries.base.BaseViewModel
import com.example.popularlibraries.navigation.UsersStarter
import com.github.terrakok.cicerone.Router
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class AppViewModel @AssistedInject constructor(
    router: Router,
    usersStarter: UsersStarter
) : BaseViewModel(router) {

    init {

    }

    fun backClicked() {
        router.exit()
    }

    @AssistedFactory
    interface Factory {

        fun create(): AppViewModel
    }
}