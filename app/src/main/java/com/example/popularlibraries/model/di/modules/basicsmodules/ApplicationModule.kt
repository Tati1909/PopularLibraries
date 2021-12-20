package com.example.popularlibraries.model.di.modules.basicsmodules

import com.example.popularlibraries.model.di.components.UsersComponent
import com.example.popularlibraries.view.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Модуль ссылается на субкомпонент GitHubUsersComponent
 * Наша зависимость:  APP -> Users -> Detail -> Info
 */
@Module(subcomponents = [UsersComponent::class])
interface ApplicationModule {

    @ContributesAndroidInjector
    fun bindMainActivity(): MainActivity

}