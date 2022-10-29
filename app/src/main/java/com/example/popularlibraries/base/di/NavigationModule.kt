package com.example.popularlibraries.base.di

import android.app.Activity
import com.example.popularlibraries.view.main.AppActivity
import com.example.popularlibraries.view.main.di.app.AppScope
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Module
import dagger.Provides
import kotlin.reflect.KClass

@Module
class NavigationModule {

    @AppScope
    @Provides
    fun provideNavigatorHolder(cicerone: Cicerone<Router>): NavigatorHolder {
        return cicerone.getNavigatorHolder()
    }

    @AppScope
    @Provides
    fun provideRouter(cicerone: Cicerone<Router>): Router {
        return cicerone.router
    }

    @AppScope
    @Provides
    fun provideCicerone(): Cicerone<Router> {
        return Cicerone.create()
    }

    @AppScope
    @Provides
    fun provideActivityClass(): KClass<out Activity> {
        return AppActivity::class
    }
}