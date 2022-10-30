package com.example.popularlibraries.view.main.di.app

import com.example.popularlibraries.base.di.CacheModule
import com.example.popularlibraries.base.di.CloudModule
import com.example.popularlibraries.base.di.CoreBinder
import com.example.popularlibraries.base.di.GithubServiceModule
import com.example.popularlibraries.base.di.NavigationModule
import com.example.popularlibraries.base.di.RouterBinder
import dagger.Module

/** Наша зависимость:  APP -> Users -> Detail -> Info */
@Module(
    includes = [
        GithubServiceModule::class,
        CacheModule::class,
        CloudModule::class,
        RouterBinder::class,
        NavigationModule::class,
        CoreBinder::class
    ]
)
interface AppModule