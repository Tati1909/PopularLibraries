package com.example.popularlibraries

import com.example.popularlibraries.model.di.components.ApplicationComponent
import com.example.popularlibraries.model.di.components.DaggerApplicationComponent
import com.example.popularlibraries.scheduler.DefaultSchedulers
import com.github.terrakok.cicerone.Cicerone
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication


/** Для Cicerone нам нужно сделать приложение single-Activity. Подключаем Cicerone в
 * проект, создаём класс App, наследуем от Application(с Dagger используем DaggerApplication)
 * и инициализируем в нём Cicerone, произведя соответствующие изменения в манифесте*/
class App : DaggerApplication() {

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this@App
    }

    override fun applicationInjector(): AndroidInjector<App> =
        applicationComponent

    /**
     * Dagger скомпилировал нам наш компонент Dagger+ApplicationComponent,
     * который создает граф зависимостей(cicerone, router, schedulers).
     */
    val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent
            .builder()
            .withContext(applicationContext)
            .apply {
                val cicerone = Cicerone.create()
                //navigatorHolder настраивается в MainActivity
                withNavigatorHolder(cicerone.getNavigatorHolder())
                withRouter(cicerone.router)
                withSchedulers(DefaultSchedulers())
            }
            .build()
    }
}