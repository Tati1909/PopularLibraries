package com.example.popularlibraries.presenter

import com.example.popularlibraries.model.GithubUsersRepo
import com.example.popularlibraries.view.users.UsersView
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter

//Необходим для навигации
//В MvpPresenter есть экземпляр класса ViewState, который реализует тот тип View, которым
//типизирован MvpPresenter - UsersView.

//В остальном всё просто:
//● при первом присоединении View вызываем метод init(), в котором напишем все операции по
//инициализации View;
//● затем получаем данные из репозитория;
//● отдаём их презентеру списка;
//● командуем View обновить список.
class UsersPresenter(val usersRepo: GithubUsersRepo, val router: Router) :
    MvpPresenter<UsersView>() {

    val usersListPresenter = UsersListPresenter()

    //Этот метод в первый раз вызывается при присоединении View к Presenter.
    //При уничтожении View, например, при повороте экрана, она отсоединится, а при пересоздании
    //присоединится вновь. И на новой View согласно стратегиям будут выполняться команды.
    //При повторном присоединении onFirstViewAttach уже вызываться не будет — это важно помнить.
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        //Чтобы воспользоваться механизмом сохранения состояния, просто
        //заменяем в Presenter все обращения к View на обращения к ViewState, доступному через геттер в
        //MvpPresenter. Проще говоря, вместо view.something() мы пишем viewState.init(). ViewState
        //сохранит вызов и добавит его в очередь, после чего проксирует вызов в нашу View.
        viewState.init()
        loadData()
        usersListPresenter.itemClickListener = { itemView ->
//TODO: переход на экран пользователя c помощью router.navigateTo
        }
    }


    fun loadData() {
        //получаем список логинов
        val users = usersRepo.getUsers()
        //добавляем список логинов в наш список презентера
        usersListPresenter.users.addAll(users)
        viewState.updateList()
    }

    ////Для обработки нажатия клавиши «Назад» добавляем функцию backPressed(). Она возвращает
    //    //Boolean, где мы передаём обработку выхода с экрана роутеру. Вообще, функции Presenter, согласно
    //    //парадигме, не должны ничего возвращать, но в нашем случае приходится идти на компромисс из-за
    //    //недостатков фреймворка.
    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}