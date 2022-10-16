package com.example.popularlibraries.view.main.di.app

import com.example.popularlibraries.base.di.CacheModule
import com.example.popularlibraries.base.di.CloudModule
import com.example.popularlibraries.base.di.GithubServiceModule
import com.example.popularlibraries.base.di.NavigationModule
import com.example.popularlibraries.base.di.RouterBinder
import com.example.popularlibraries.scheduler.DefaultSchedulers
import com.example.popularlibraries.scheduler.Schedulers
import dagger.Binds
import dagger.Module

/**
 * Модуль ссылается на субкомпонент GitHubUsersComponent
 * Наша зависимость:  APP -> Users -> Detail -> Info
 */
@Module(
    includes = [
        GithubServiceModule::class,
        CacheModule::class,
        CloudModule::class,
        RouterBinder::class,
        NavigationModule::class
    ]
)
interface AppModule {

    /*companion object {

        @Provides
        @AppScope
        fun provideGithubService(retrofit: Retrofit): GithubService = retrofit.create()
    }*/

    @Binds
    fun provideShedulers(defaultSchedulers: DefaultSchedulers): Schedulers
}