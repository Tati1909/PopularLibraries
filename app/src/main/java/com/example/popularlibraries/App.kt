package com.example.popularlibraries

import android.app.Application
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router

//Для Cicerone нам нужно сделать приложение single-Activity. Подключаем Cicerone в
//проект, создаём класс App, наследуем от Application и инициализируем в нём Cicerone, произведя
//соответствующие изменения в манифесте:
class App : Application() {

    companion object {
        lateinit var instance: App
    }

    //Временно до даггера положим это тут
    private val cicerone: Cicerone<Router> by lazy {
        Cicerone.create()
    }

    //navigatorHolder настраивается в MainActivity
    val navigatorHolder get() = cicerone.getNavigatorHolder()
    val router get() = cicerone.router

    override fun onCreate() {
        super.onCreate()
        instance = this@App
    }
}