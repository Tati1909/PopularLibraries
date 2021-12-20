package com.example.popularlibraries.model.di.modules.basicsmodules

import com.example.popularlibraries.model.di.modules.UsersRepositoryModule
import com.example.popularlibraries.view.users.UsersFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [UsersRepositoryModule::class])
interface UsersModule {

    @ContributesAndroidInjector
    fun bindUsersFragment(): UsersFragment
}