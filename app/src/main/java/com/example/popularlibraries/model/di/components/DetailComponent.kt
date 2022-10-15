package com.example.popularlibraries.model.di.components

import com.example.popularlibraries.model.di.modules.basicsmodules.DetailModule
import com.example.popularlibraries.view.details.DetailsFragment
import dagger.Subcomponent

@GitHubUser
@Subcomponent(modules = [DetailModule::class])
interface DetailComponent {

    fun inject(detailsFragment: DetailsFragment)

    @Subcomponent.Builder
    interface Builder {

        fun build(): DetailComponent

    }
}