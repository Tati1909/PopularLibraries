package com.example.popularlibraries.view.details.di

import com.example.popularlibraries.view.details.DetailsFragment
import dagger.Component

@Component(dependencies = [DetailsDependencies::class])
interface DetailComponent {

    fun inject(detailsFragment: DetailsFragment)

    companion object {

        fun build(detailsDependencies: DetailsDependencies): DetailComponent =
            DaggerDetailComponent.builder()
                .detailsDependencies(detailsDependencies)
                .build()
    }
}