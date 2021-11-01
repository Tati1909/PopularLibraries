package com.example.popularlibraries.view

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

//Так как всё, что появится на экране — просто список, интерфейс включает всего два метода:
//● init() — для первичной инициализации списка, который мы будем вызывать при
//присоединении View к Presenter;
//● updateList() — для обновления содержимого списка.
@StateStrategyType(AddToEndSingleStrategy::class)
interface UsersView : MvpView {
    fun init()
    fun updateList()
}