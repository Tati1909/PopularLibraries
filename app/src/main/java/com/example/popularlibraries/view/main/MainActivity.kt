package com.example.popularlibraries.view.main

import android.os.Bundle
import android.widget.Toast
import com.example.popularlibraries.R
import com.example.popularlibraries.navigation.BackButtonListener
import com.example.popularlibraries.networkstatus.NetworkStateObservable
import com.example.popularlibraries.view.inject.DaggerMvpActivity
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import moxy.ktx.moxyPresenter
import javax.inject.Inject

/**Навигатор отсоединяется в onPause и присоединяется в onResumeFragments, чтобы при переходе в
//другой Activity, который тоже может иметь свой навигатор, вызовы передавались навигатору нового
//открытого Activity. Новый открытый Activity также присоединится при запуске и отсоединится в
//onPause и т. д
 */
class MainActivity : DaggerMvpActivity(R.layout.activity_main), MainView {

    private val navigator = AppNavigator(this@MainActivity, R.id.container)

    /** Объявляем Presenter и делегируем его создание и хранение через делегат moxyPresenter.
    moxyPresenter создает новый экземпляр MoxyKtxDelegate.
    Делегат подключается к жизненному циклу активити */
    private val presenter by moxyPresenter {
        MainPresenter(router)
    }

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var router: Router

    private val disposables = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    /**
     * Подписываемя на слушателя наличия сети:
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

    }

    /**
     * устанавливаем навигатор
     */
    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach {
            if (it is BackButtonListener && it.backPressed()) {
                return
            }
        }
        presenter.backClicked()
    }

    override fun onDestroy() {
        super.onDestroy()

        disposables.dispose()
    }
}