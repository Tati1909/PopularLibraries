package com.example.popularlibraries.view.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.popularlibraries.R
import com.example.popularlibraries.base.HandlingNavigator
import com.example.popularlibraries.base.di.ComponentDependenciesProvider
import com.example.popularlibraries.base.di.HasComponentDependencies
import com.example.popularlibraries.base.di.findComponentDependencies
import com.example.popularlibraries.navigation.BackButtonListener
import com.example.popularlibraries.navigation.UsersStarter
import com.example.popularlibraries.networkstatus.NetworkStateObservable
import com.example.popularlibraries.view.main.di.activity.AppActivityComponent
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import javax.inject.Inject

/**Навигатор отсоединяется в onPause и присоединяется в onResumeFragments, чтобы при переходе в
//другой Activity, который тоже может иметь свой навигатор, вызовы передавались навигатору нового
//открытого Activity. Новый открытый Activity также присоединится при запуске и отсоединится в
//onPause и т. д
 */
class AppActivity : AppCompatActivity(), HasComponentDependencies {

    @Inject lateinit var viewModelFactory: AppViewModel.Factory
    @Inject lateinit var navigatorHolder: NavigatorHolder
    @Inject lateinit var router: Router
    @Inject lateinit var usersStarter: UsersStarter
    @Inject override lateinit var dependencies: ComponentDependenciesProvider

    private lateinit var navigator: HandlingNavigator
    private val viewModel: AppViewModel by lazy { viewModelFactory.create() }
    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent?.inject(this)
        super.onCreate(savedInstanceState)

        navigator = AppActivityComponent.build(findComponentDependencies()).provideNavigatorFactory().init(this)

        setContentView(R.layout.activity_main)

        /**
         * Подписываемся на слушателя наличия сети:
         * NetworkState: CONNECTED
         * NetworkState: DISCONNECTED
         */
        disposables +=
            NetworkStateObservable(this)
                .subscribe { networkState ->
                    Toast.makeText(
                        this,
                        "NetworkState: $networkState",
                        Toast.LENGTH_SHORT
                    ).show()
                }

        router.newRootScreen(usersStarter.users())
    }

    /** устанавливаем навигатоp */
    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach {
            if (it is BackButtonListener && it.backPressed()) {
                return
            }
        }
        viewModel.backClicked()
    }

    override fun onDestroy() {
        super.onDestroy()

        disposables.dispose()
    }
}