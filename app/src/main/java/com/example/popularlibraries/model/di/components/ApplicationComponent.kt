package com.example.popularlibraries.model.di.components

import android.content.Context
import com.example.popularlibraries.App
import com.example.popularlibraries.model.di.modules.basicsmodules.ApplicationModule
import com.example.popularlibraries.scheduler.Schedulers
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

/**
 * @Component отмечает интерфейс ApplicationComponent, который позже используется для генерации кода. В нём
 * определяется, куда что-либо внедрять, а также методы для прямого доступа к зависимостям.
 * Имплементим AndroidInjector<App>, чтобы не писать inject-методы.
 */
@Component(
    modules = [
        AndroidInjectionModule::class,
        ApplicationModule::class
    ]
)
interface ApplicationComponent : AndroidInjector<App> {

    /**
     * функции возвращают Builder определенного субкомпонента
     */
    fun gitHubUsersComponent(): UsersComponent.Builder
    fun gitHubUserComponent(): DetailComponent.Builder
    fun gitHubInfoComponent(): InfoComponent.Builder

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