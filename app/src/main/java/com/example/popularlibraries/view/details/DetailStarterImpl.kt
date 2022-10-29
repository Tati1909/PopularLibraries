package com.example.popularlibraries.view.details

import com.example.popularlibraries.model.datasource.GithubUser
import com.example.popularlibraries.navigation.DetailsStarter
import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import javax.inject.Inject

class DetailsStarterImpl @Inject constructor() : DetailsStarter {

    override fun details(user: GithubUser): Screen {
        return FragmentScreen { DetailsFragment.newInstance(user.login) }
    }
}