package com.example.popularlibraries.view.users

import com.example.popularlibraries.navigation.UsersStarter
import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import javax.inject.Inject

class UsersStarterImpl @Inject constructor() : UsersStarter {

    //переход на фрагмент со списком пользователей UsersFragment
    override fun users(): Screen {
        return FragmentScreen { UsersFragment.newInstance() }
    }
}