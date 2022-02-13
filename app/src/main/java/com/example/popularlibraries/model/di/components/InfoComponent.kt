package com.example.popularlibraries.model.di.components

import com.example.popularlibraries.model.di.modules.basicsmodules.InfoModule
import com.example.popularlibraries.view.info.InfoFragment
import dagger.Subcomponent

@GitHubInfo
@Subcomponent(modules = [InfoModule::class])
interface InfoComponent {

    fun inject(infoFragment: InfoFragment)

    @Subcomponent.Builder
    interface Builder {

        fun build(): InfoComponent
    }
}