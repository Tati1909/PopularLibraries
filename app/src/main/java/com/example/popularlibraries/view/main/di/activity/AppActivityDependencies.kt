package com.example.popularlibraries.view.main.di.activity

import com.example.popularlibraries.base.di.ComponentDependencies
import com.example.popularlibraries.scheduler.DefaultSchedulers
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router

interface AppActivityDependencies : ComponentDependencies {

    fun navigatorHolder(): NavigatorHolder
    fun router(): Router
    fun defaultSchedulers(): DefaultSchedulers
}