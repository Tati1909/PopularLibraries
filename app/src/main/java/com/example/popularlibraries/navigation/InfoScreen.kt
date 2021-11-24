package com.example.popularlibraries.navigation

import com.example.popularlibraries.view.info.InfoFragment
import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.FragmentScreen

class InfoScreen(private val repositoryUrl: String) {

    //переход на фрагмент с одним пользователем DetailFragment
    fun create(): Screen {
        return FragmentScreen { InfoFragment.newInstance(repositoryUrl) }
    }
}