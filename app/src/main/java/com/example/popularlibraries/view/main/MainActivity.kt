package com.example.popularlibraries.view.main

import android.os.Bundle
import android.widget.Toast
import com.example.popularlibraries.App
import com.example.popularlibraries.R
import com.example.popularlibraries.databinding.ActivityMainBinding
import com.example.popularlibraries.navigation.BackButtonListener
import com.example.popularlibraries.networkstatus.NetworkStateObservable
import com.github.terrakok.cicerone.androidx.AppNavigator
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter

//Класс SupportAppNavigator — часть Cicerone. Именно внутри него происходит вся работа с
//FragmentManager в зависимости от отправляемых нами навигационных команд.
//Навигатор отсоединяется в onPause и присоединяется в onResumeFragments, чтобы при переходе в
//другой Activity, который тоже может иметь свой навигатор, вызовы передавались навигатору нового
//открытого Activity. Новый открытый Activity также присоединится при запуске и отсоединится в
//onPause и т. д.
//Используется именно onResumeFragments, а не onResume. Это делается потому, что в момент
//onResume некоторые фрагменты Activity могут ещё не достичь своих onResume. Иногда это
//приводит к состоянию, в котором запрещены транзакции фрагментов, а попытки навигации могут
//привести к IllegalStateException. Во время реализации onResumeFragments все фрагменты уже
//восстановлены и находятся в правильном состоянии.
//Глядя на код, мы понимаем манипуляции с BackButtonListener и с возвращаемым значением из
//соответствующих функций Presenter-фрагментов: при нажатии кнопки «Назад» проходим по всем
//фрагментам в стеке и, встретив первый, реализующий интерфейс, вызываем у него функцию
//backPressed(). Такой подход к обработке переходов вверх по стеку — стандартная практика при
//работе с фрагментами.
//Если оттуда возвращается true, значит, событие поглощено, и никаких действий предпринимать не
//требуется. А если наоборот — сообщаем презентеру о необходимости обработки нажатия. Остальное
//— организация работы Cicerone, подробнее о которой — в теоретической части.
class MainActivity : MvpAppCompatActivity(), MainView {

    private val navigator = AppNavigator(this@MainActivity, R.id.container)

    //объявляем Presenter и делегируем его создание и хранение
    //через делегат moxyPresenter.
    //moxyPresenter создает новый экземпляр MoxyKtxDelegate.
    //Делегат подключается к жизненному циклу активити
    private val presenter by moxyPresenter {
        MainPresenter(App.instance.router)
    }

    private var binding: ActivityMainBinding? = null

    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

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

    //устанавливаем навигатор
    override fun onResumeFragments() {
        super.onResumeFragments()
        App.instance.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        App.instance.navigatorHolder.removeNavigator()
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