package com.example.popularlibraries.view.main.di.app

import android.app.Application
import android.content.Context
import com.example.popularlibraries.base.di.ComponentDependencies
import com.example.popularlibraries.base.di.ComponentDependenciesKey
import com.example.popularlibraries.base.di.ComponentDependenciesProvider
import com.example.popularlibraries.view.details.di.DetailsDependencies
import com.example.popularlibraries.view.info.di.InfoDependencies
import com.example.popularlibraries.view.main.App
import com.example.popularlibraries.view.main.AppActivity
import com.example.popularlibraries.view.main.di.activity.AppActivityDependencies
import com.example.popularlibraries.view.users.di.UsersDependencies
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap
import dagger.multibindings.Multibinds

/**
 * @Component отмечает интерфейс ApplicationComponent, который позже используется для генерации кода. В нём
 * определяется, куда что-либо внедрять, а также методы для прямого доступа к зависимостям.
 */
@AppScope
@Component(
    modules = [
        ComponentDependenciesModule::class,
        AppModule::class
    ]
)
interface AppComponent :
    AppActivityDependencies,
    UsersDependencies,
    DetailsDependencies,
    InfoDependencies {

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance context: Context, @BindsInstance application: Application): AppComponent
    }

    fun inject(application: App)
    fun inject(activity: AppActivity)
}

@Module
interface ComponentDependenciesModule {

    @Binds
    @IntoMap
    @ComponentDependenciesKey(UsersDependencies::class)
    fun provideUsersDependencies(component: AppComponent): ComponentDependencies

    @Binds
    @IntoMap
    @ComponentDependenciesKey(DetailsDependencies::class)
    fun provideDetailsDependencies(component: AppComponent): ComponentDependencies

    @Binds
    @IntoMap
    @ComponentDependenciesKey(InfoDependencies::class)
    fun provideInfoDependencies(component: AppComponent): ComponentDependencies

    @Binds
    @IntoMap
    @ComponentDependenciesKey(AppActivityDependencies::class)
    fun provideAppActivityDependencies(component: AppComponent): ComponentDependencies

    @Multibinds
    fun componentDependencies(): ComponentDependenciesProvider
}