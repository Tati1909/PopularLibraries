package com.example.popularlibraries.view.details.di

import com.example.popularlibraries.base.di.ComponentDependencies
import com.example.popularlibraries.base.resourcesprovider.ResourcesProvider
import com.example.popularlibraries.model.repository.GithubUsersRepository
import com.example.popularlibraries.navigation.InfoStarter
import com.github.terrakok.cicerone.Router

interface DetailsDependencies : ComponentDependencies {

    fun router(): Router
    fun githubUsersRepository(): GithubUsersRepository
    fun infoStarter(): InfoStarter
    fun resourcesProvider(): ResourcesProvider
}