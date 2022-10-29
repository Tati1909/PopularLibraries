package com.example.popularlibraries.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.example.popularlibraries.R
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class HandlingNavigator @AssistedInject constructor(
    private val router: Router,
    private val navigationHolder: NavigatorHolder,
    @Assisted activity: FragmentActivity
) : AppNavigator(activity, R.id.container) {

    override fun setupFragmentTransaction(
        screen: FragmentScreen,
        fragmentTransaction: FragmentTransaction,
        currentFragment: Fragment?,
        nextFragment: Fragment
    ) {
        with(fragmentTransaction) {
            // Fix incorrect order lifecycle callback of MainFragment
            setReorderingAllowed(true)
        }
    }
}