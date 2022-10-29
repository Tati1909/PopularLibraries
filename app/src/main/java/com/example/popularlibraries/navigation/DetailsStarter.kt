package com.example.popularlibraries.navigation

import com.example.popularlibraries.model.datasource.GithubUser
import com.github.terrakok.cicerone.Screen

interface DetailsStarter {

    //переход на фрагмент с одним пользователем DetailFragment
    fun details(user: GithubUser): Screen
}