package com.example.popularlibraries.view

import com.github.terrakok.cicerone.androidx.FragmentScreen

//Классы Screen и FragmentScreen — часть Cicerone, причём второй — наследник первого. В его
//конструктор мы передаём функтор, создающий фрагмент. Такой фрагмент представляет собой экран.
//В дальнейшем функтор вызывается внутри навигатора при получении навигационных команд.
class AndroidScreens : IScreens {
    override fun users() = FragmentScreen { UsersFragment.newInstance() }
}