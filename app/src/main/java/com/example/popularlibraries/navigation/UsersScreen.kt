package com.example.popularlibraries.navigation

import com.example.popularlibraries.view.users.UsersFragment
import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.FragmentScreen

//Классы Screen и FragmentScreen — часть Cicerone, причём FragmentScreen наследует Screen. В его
//конструктор мы передаём функтор, создающий фрагмент. Такой фрагмент представляет собой экран.
//В дальнейшем функтор вызывается внутри навигатора при получении навигационных команд.
object UsersScreen {

    //переход на фрагмент со списком пользователей UsersFragment
    fun create(): Screen {
        return FragmentScreen { UsersFragment.newInstance() }
    }
}