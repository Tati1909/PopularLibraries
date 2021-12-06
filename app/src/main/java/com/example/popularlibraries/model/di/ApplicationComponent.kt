package com.example.popularlibraries.model.di

import android.content.Context
import com.example.popularlibraries.App
import com.example.popularlibraries.model.di.modules.ApiModule
import com.example.popularlibraries.model.di.modules.CacheModule
import com.example.popularlibraries.model.di.modules.UiModule
import com.example.popularlibraries.model.di.modules.UsersModule
import com.example.popularlibraries.scheduler.Schedulers
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

/**
 * @Component отмечает интерфейс ApplicationComponent, который позже используется для генерации кода. В нём
 * определяется, куда что-либо внедрять, а также методы для прямого доступа к зависимостям.
 * Имплементим AndroidInjector<App>, чтобы не писать inject-методы.
 */
@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        UiModule::class,
        UsersModule::class,
        ApiModule::class,
        CacheModule::class
    ]
)
interface ApplicationComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun withContext(context: Context): Builder

        @BindsInstance
        fun withRouter(router: Router): Builder

        @BindsInstance
        fun withNavigatorHolder(navigatorHolder: NavigatorHolder): Builder

        @BindsInstance
        fun withSchedulers(schedulers: Schedulers): Builder

        fun build(): ApplicationComponent
    }
}