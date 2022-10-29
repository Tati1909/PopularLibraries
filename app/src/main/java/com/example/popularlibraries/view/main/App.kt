package com.example.popularlibraries.view.main

import android.app.Application
import com.example.popularlibraries.base.di.ComponentDependenciesProvider
import com.example.popularlibraries.base.di.HasComponentDependencies
import com.example.popularlibraries.view.main.di.app.AppComponent
import com.example.popularlibraries.view.main.di.app.DaggerAppComponent
import javax.inject.Inject

/** Для Cicerone нам нужно сделать приложение single-Activity. Подключаем Cicerone в
 * проект, создаём класс App, наследуем от Application(с Dagger используем DaggerApplication)
 * и инициализируем в нём Cicerone, произведя соответствующие изменения в манифесте */
class App : Application(), HasComponentDependencies {

    @Inject override lateinit var dependencies: ComponentDependenciesProvider

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(context = this, application = this)
        appComponent?.inject(this)
    }

    companion object {

        var appComponent: AppComponent? = null
    }
}