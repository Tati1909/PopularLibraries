package com.example.popularlibraries.model.di.modules.basicsmodules

import com.example.popularlibraries.model.di.modules.InfoRepositoryModule
import com.example.popularlibraries.view.info.InfoFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [InfoRepositoryModule::class])
interface InfoModule {

    @ContributesAndroidInjector
    fun bindInfoFragment(): InfoFragment
}