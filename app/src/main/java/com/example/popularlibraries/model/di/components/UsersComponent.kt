package com.example.popularlibraries.model.di.components

import com.example.popularlibraries.model.di.modules.GitHubUsers
import com.example.popularlibraries.model.di.modules.basicsmodules.UsersModule
import com.example.popularlibraries.view.users.UsersFragment
import dagger.Subcomponent

/**
 * У каждого субкомпонента есть свой @Scope,
 * т. к. Dagger должен их различать.
 */
@GitHubUsers
@Subcomponent(modules = [UsersModule::class])
interface UsersComponent {

    fun inject(usersFragment: UsersFragment)

    @Subcomponent.Builder
    interface Builder {

        fun build(): UsersComponent

    }
}