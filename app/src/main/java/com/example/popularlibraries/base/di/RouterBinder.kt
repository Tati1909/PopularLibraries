package com.example.popularlibraries.base.di

import com.example.popularlibraries.navigation.DetailsStarter
import com.example.popularlibraries.navigation.InfoStarter
import com.example.popularlibraries.navigation.UsersStarter
import com.example.popularlibraries.view.details.DetailsStarterImpl
import com.example.popularlibraries.view.info.InfoStarterImpl
import com.example.popularlibraries.view.users.UsersStarterImpl
import dagger.Binds
import dagger.Module

@Module
interface RouterBinder {

    @Binds
    fun provideUsersStarter(usersStarter: UsersStarterImpl): UsersStarter

    @Binds
    fun provideDetailsStarter(detailsStarter: DetailsStarterImpl): DetailsStarter

    @Binds
    fun provideInfoStarter(infoStarter: InfoStarterImpl): InfoStarter
}