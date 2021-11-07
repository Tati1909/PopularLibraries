package com.example.popularlibraries.presenter

import com.example.popularlibraries.model.GithubUser
import com.example.popularlibraries.model.GithubUsersRepo
import com.example.popularlibraries.navigation.DetailScreen
import com.example.popularlibraries.view.users.UsersRVAdapter
import com.example.popularlibraries.view.users.UsersView
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter

//Router необходим для навигации.
//В MvpPresenter есть экземпляр класса ViewState, который реализует тот тип View, которым
//типизирован MvpPresenter - UsersView.

//В остальном всё просто:
//● при первом присоединении View вызываем метод init(), в котором напишем все операции по
//инициализации View;
//● затем получаем данные из репозитория;
//● отдаём их презентеру списка;
//● командуем viewState обновить список.
class UsersPresenter(
    private val model: GithubUsersRepo, private val router: Router
) :
    MvpPresenter<UsersView>() {

    //В презентере нам нужен список пользователей, который мы будем заполнять и обрабатывать нажатие на элемент,
    //поэтому создаем класс UsersListPresenter, который имплементит IUserListPresenter, а он в свою очередь - IListPresenter
    //с общими методами для обработки списка bindView() и getCount().
    //В UsersListPresenter создаем список пользователей из модели, который потом
    //присвоим списку пользователей в презентере UsersPresenter
    class UsersListPresenter : IUserListPresenter {

        val usersList = mutableListOf<GithubUser>()

        override var itemClickListener: ((UsersRVAdapter.UserItemView) -> Unit)? = null

        override fun getCount() = usersList.size

        override fun bindView(view: UsersRVAdapter.UserItemView) {
            val user = usersList[view.pos]
            view.setLogin(user.login)
        }
    }

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
    }

    private fun loadData() {
        //получаем список логинов из модели репозитория
        val users: List<GithubUser> = model.getUsers()
        //добавляем список логинов из модели в наш список презентера
        //теперь в usersListPresenter весь список пользователей
        usersListPresenter.usersList.addAll(users)
        //переход на экран пользователя c помощью router.navigateTo
        //itemClickListener - это переменная в виде функции, которая принимает userItemView:  UsersRVAdapter.UserItemView
        // и ничего не возвращает
        usersListPresenter.itemClickListener = { userItemView: UsersRVAdapter.UserItemView ->
            //(users[userItemView.pos]) - это выбранный объект списка пользователей
            //при нажатии на элемент создаем объект DetailScreen и вызываем метод create,
            //который в свою очередь создает Detailfragment и ложит имя пользователя в корзину
            router.navigateTo(DetailScreen(users[userItemView.pos]).create())
        }

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