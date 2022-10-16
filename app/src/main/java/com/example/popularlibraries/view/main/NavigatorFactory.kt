package com.example.popularlibraries.view.main

import androidx.fragment.app.FragmentActivity
import com.example.popularlibraries.base.HandlingNavigator
import dagger.assisted.AssistedFactory

@AssistedFactory
interface NavigatorFactory {

    fun init(activity: FragmentActivity): HandlingNavigator
}