package com.example.popularlibraries.view.users

import com.example.popularlibraries.model.datasource.GithubUser
import com.example.popularlibraries.view.ScreenView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

//Так как всё, что появится на экране UsersFragment — просто список, интерфейс включает метод init.
//● init() — для первичной инициализации списка, который мы будем вызывать при
//присоединении View к Presenter;
//Этот метод будет (может) выполнять наша viewState в презентере, т. к. ScreenView имплементит MvpView, а
//также UsersFragment(должен обязательно имплементировать эти методы)

@StateStrategyType(AddToEndSingleStrategy::class)
interface UsersView : ScreenView {

    fun init(users: List<GithubUser>)
}