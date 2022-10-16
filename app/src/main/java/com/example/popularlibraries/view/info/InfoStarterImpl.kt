package com.example.popularlibraries.view.info

import com.example.popularlibraries.navigation.InfoStarter
import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import javax.inject.Inject

class InfoStarterImpl @Inject constructor() : InfoStarter {

    //переход на фрагмент с одним пользователем DetailFragment
    override fun info(repositoryUrl: String): Screen {
        return FragmentScreen { InfoFragment.newInstance(repositoryUrl) }
    }
}