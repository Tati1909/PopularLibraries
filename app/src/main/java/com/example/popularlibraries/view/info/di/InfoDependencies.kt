package com.example.popularlibraries.view.info.di

import com.example.popularlibraries.base.di.ComponentDependencies
import com.example.popularlibraries.base.resourcesprovider.ResourcesProvider
import com.example.popularlibraries.model.repository.GithubUsersRepository
import com.example.popularlibraries.scheduler.Schedulers
import com.github.terrakok.cicerone.Router

interface InfoDependencies : ComponentDependencies {

    fun githubUsersRepository(): GithubUsersRepository
    fun router(): Router
    fun schedulers(): Schedulers
    fun resourcesProvider(): ResourcesProvider
}