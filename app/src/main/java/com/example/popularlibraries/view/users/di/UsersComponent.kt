package com.example.popularlibraries.view.users.di

import com.example.popularlibraries.view.users.UsersFragment
import dagger.Component

@Component(dependencies = [UsersDependencies::class])
interface UsersComponent {

    fun inject(usersFragment: UsersFragment)

    companion object {

        fun build(usersDependencies: UsersDependencies): UsersComponent =
            DaggerUsersComponent.builder()
                .usersDependencies(usersDependencies)
                .build()
    }
}