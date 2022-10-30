package com.example.popularlibraries.view.users.di

import com.example.popularlibraries.base.di.ComponentDependencies
import com.example.popularlibraries.base.resourcesprovider.ResourcesProvider
import com.example.popularlibraries.model.cloud.CloudUserDataSource
import com.example.popularlibraries.model.repository.GithubUsersRepository
import com.example.popularlibraries.model.storage.CacheUserDataSource
import com.example.popularlibraries.navigation.DetailsStarter
import com.github.terrakok.cicerone.Router

interface UsersDependencies : ComponentDependencies {

    fun githubUsersRepository(): GithubUsersRepository
    fun cloudUserDataSource(): CloudUserDataSource
    fun cacheUserDataSource(): CacheUserDataSource
    fun router(): Router
    fun detailsStarter(): DetailsStarter
    fun resourcesProvider(): ResourcesProvider
}