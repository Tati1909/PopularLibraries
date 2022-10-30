package com.example.popularlibraries.base.di

import com.example.popularlibraries.base.resourcesprovider.ResourcesProvider
import com.example.popularlibraries.base.resourcesprovider.ResourcesProviderImpl
import com.example.popularlibraries.view.main.di.app.AppScope
import dagger.Binds
import dagger.Module

@Module
interface CoreBinder {

    @Binds
    @AppScope
    fun provideResourcesProvider(resourcesProviderImpl: ResourcesProviderImpl): ResourcesProvider
}