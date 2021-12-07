package com.example.popularlibraries.model.di.modules

import com.example.popularlibraries.view.details.DetailFragment
import com.example.popularlibraries.view.info.InfoFragment
import com.example.popularlibraries.view.main.MainActivity
import com.example.popularlibraries.view.users.UsersFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
interface UiModule {

    @ContributesAndroidInjector
    fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    fun bindUsersFragment(): UsersFragment

    @ContributesAndroidInjector
    fun bindDetailsFragment(): DetailFragment

    @ContributesAndroidInjector
    fun bindInfoFragment(): InfoFragment
}