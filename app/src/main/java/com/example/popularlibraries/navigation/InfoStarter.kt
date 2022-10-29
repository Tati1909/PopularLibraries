package com.example.popularlibraries.navigation

import com.github.terrakok.cicerone.Screen

interface InfoStarter {

    //переход на фрагмент с одним пользователем DetailFragment
    fun info(repositoryUrl: String): Screen
}