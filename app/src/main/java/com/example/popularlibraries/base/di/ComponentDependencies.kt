package com.example.popularlibraries.base.di

import android.app.Application
import android.app.Service
import android.content.Context
import androidx.fragment.app.Fragment
import dagger.MapKey
import kotlin.reflect.KClass

interface ComponentDependencies

inline fun <reified T : ComponentDependencies> Fragment.findComponentDependencies(): T =
    findComponentDependenciesProvider()[T::class.java] as T

inline fun <reified T : ComponentDependencies> Service.findComponentDependencies(): T =
    findComponentDependenciesProvider()[T::class.java] as T

inline fun <reified T : ComponentDependencies> Context.findComponentDependencies(): T =
    findComponentDependenciesProvider(applicationContext as Application)[T::class.java] as T

typealias ComponentDependenciesProvider =
    Map<Class<out ComponentDependencies>, @JvmSuppressWildcards ComponentDependencies>

interface HasComponentDependencies {

    val dependencies: ComponentDependenciesProvider
}

@MapKey
@Target(AnnotationTarget.FUNCTION)
annotation class ComponentDependenciesKey(val value: KClass<out ComponentDependencies>)

fun Fragment.findComponentDependenciesProvider(): ComponentDependenciesProvider {
    var current: Fragment? = this
    while (current !is HasComponentDependencies?) {
        current = current?.parentFragment
    }

    val hasDaggerProviders = current ?: when {
        activity is HasComponentDependencies -> activity as HasComponentDependencies
        activity?.application is HasComponentDependencies -> activity?.application as HasComponentDependencies
        else -> throw IllegalStateException("Can not find suitable dagger provider for $this")
    }
    return hasDaggerProviders.dependencies
}

fun Service.findComponentDependenciesProvider(): ComponentDependenciesProvider =
    when {
        this is HasComponentDependencies -> this
        this.application is HasComponentDependencies -> this.application as HasComponentDependencies
        else -> throw IllegalStateException("Can not find suitable dagger provider for $this")
    }.dependencies

fun Context.findComponentDependenciesProvider(app: Application): ComponentDependenciesProvider =
    when {
        this is HasComponentDependencies -> this
        app is HasComponentDependencies -> app
        else -> throw IllegalStateException("Can not find suitable dagger provider for $this")
    }.dependencies