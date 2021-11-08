package com.example.popularlibraries.view.details

import com.example.popularlibraries.model.GithubUser
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

//Так как всё, что появится на экране — просто логин, интерфейс включает всего 1 метод:
//● init() — для первичной инициализации списка, который мы будем вызывать при
//присоединении View к Presenter;
@StateStrategyType(AddToEndSingleStrategy::class)
interface DetailsView : MvpView {

    fun showUser(user: GithubUser)
}