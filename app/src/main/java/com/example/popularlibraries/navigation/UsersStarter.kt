package com.example.popularlibraries.navigation

import com.github.terrakok.cicerone.Screen

//Классы Screen и FragmentScreen — часть Cicerone, причём FragmentScreen наследует Screen. В его
//конструктор мы передаём функтор, создающий фрагмент. Такой фрагмент представляет собой экран.
//В дальнейшем функтор вызывается внутри навигатора при получении навигационных команд.
interface UsersStarter {

    fun users(): Screen
}