package com.example.popularlibraries.model.di.modules.basicsmodules

import com.example.popularlibraries.model.di.modules.DetailRepositoryModule
import com.example.popularlibraries.view.details.DetailsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [DetailRepositoryModule::class])
interface DetailModule {

    @ContributesAndroidInjector
    fun bindDetailsFragment(): DetailsFragment
}