package com.example.popularlibraries.view

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

//Интерфейс для обработки ошибки в презентере через Viewstate
//UsersView и detailsView имплементят ScreenView
@StateStrategyType(AddToEndSingleStrategy::class)
interface ScreenView : MvpView {

    fun showError(error: Throwable)
}