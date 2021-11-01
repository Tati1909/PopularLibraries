package com.example.popularlibraries

import android.os.Bundle
import com.example.popularlibraries.databinding.ActivityMainBinding
import com.example.popularlibraries.presenter.MainPresenter
import com.example.popularlibraries.view.AndroidScreens
import com.example.popularlibraries.view.BackButtonListener
import com.example.popularlibraries.view.MainView
import com.github.terrakok.cicerone.androidx.AppNavigator
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
    val navigator = AppNavigator(this, R.id.container)
    private val presenter by moxyPresenter { MainPresenter(App.instance.router,
        AndroidScreens()
    ) }
    private var vb: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityMainBinding.inflate(layoutInflater)
        setContentView(vb?.root)
    }
    override fun onResumeFragments() {
        super.onResumeFragments()
        App.instance.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        App.instance.navigatorHolder.removeNavigator()
    }
    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach {
            if(it is BackButtonListener && it.backPressed()){
                return
            }
        }
        presenter.backClicked()
    }
}