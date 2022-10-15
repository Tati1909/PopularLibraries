package com.example.popularlibraries.navigation

import com.example.popularlibraries.model.datasource.GithubUser
import com.example.popularlibraries.view.details.DetailsFragment
import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.FragmentScreen

class DetailScreen(private val user: GithubUser) {

    //переход на фрагмент с одним пользователем DetailFragment
    fun create(): Screen {
        return FragmentScreen { DetailsFragment.newInstance(user.login) }
    }
}