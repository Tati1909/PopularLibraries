package com.example.popularlibraries.view.main.di.activity

import com.example.popularlibraries.view.main.NavigatorFactory
import dagger.Component
import javax.inject.Singleton

@Component(dependencies = [AppActivityDependencies::class])
@Singleton
interface AppActivityComponent {

    fun provideNavigatorFactory(): NavigatorFactory

    companion object {

        fun build(appActivityDependencies: AppActivityDependencies): AppActivityComponent =
            DaggerAppActivityComponent.builder()
                .appActivityDependencies(appActivityDependencies)
                .build()
    }
}