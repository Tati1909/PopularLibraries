package com.example.popularlibraries.presenter

import com.example.popularlibraries.model.GithubUser
import com.example.popularlibraries.model.GithubUserRepository
import com.example.popularlibraries.navigation.DetailScreen
import com.example.popularlibraries.view.users.UsersView
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter

//Router необходим для навигации.
//В MvpPresenter есть экземпляр класса ViewState, который реализует тот тип View, которым
//типизирован MvpPresenter - UsersView.
//В остальном всё просто:
//● получаем данные из репозитория;
//● при первом присоединении View вызываем метод init(), в котором напишем все операции по
//инициализации View;
class UsersPresenter(
    private val model: GithubUserRepository,
    private val router: Router
) : MvpPresenter<UsersView>() {

    //Этот метод в первый раз вызывается при присоединении View к Presenter.
    //При уничтожении View, например, при повороте экрана, она отсоединится, а при пересоздании
    //присоединится вновь. И на новой View согласно стратегиям будут выполняться команды.
    //При повторном присоединении onFirstViewAttach уже вызываться не будет — это важно помнить.
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        //Чтобы воспользоваться механизмом сохранения состояния, просто
        //заменяем в Presenter все обращения к View на обращения к ViewState.
        // Проще говоря, вместо view.something() мы пишем viewState.init().

        //создаем список пользователей из модели
        val users: List<GithubUser> = model.getUsers()

        users.let(viewState::init)

    }

    /**переход на экран пользователя c помощью router.navigateTo
    //при нажатии на элемент создаем объект DetailScreen и вызываем метод create,
    //который в свою очередь создает Detailfragment и ложит имя пользователя в корзину
     */
    fun displayUser(user: GithubUser) =
        router.navigateTo(DetailScreen(user).create())

    /**Для обработки нажатия клавиши «Назад» добавляем функцию backPressed(). Она возвращает
    Boolean, где мы передаём обработку выхода с экрана роутеру. Вообще, функции Presenter, согласно
    парадигме, не должны ничего возвращать, но в нашем случае приходится идти на компромисс из-за
    недостатков фреймворка.*/
    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}