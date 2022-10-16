package com.example.popularlibraries.view.info.di

import com.example.popularlibraries.view.info.InfoFragment
import dagger.Component

@Component(dependencies = [InfoDependencies::class])
interface InfoComponent {

    fun inject(infoFragment: InfoFragment)

    companion object {

        fun build(infoDependencies: InfoDependencies): InfoComponent =
            DaggerInfoComponent.builder()
                .infoDependencies(infoDependencies)
                .build()
    }
}