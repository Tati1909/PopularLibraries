package com.example.popularlibraries.view.users

import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.popularlibraries.model.datasource.GithubUser
import com.example.popularlibraries.model.repository.GithubUsersRepository
import com.example.popularlibraries.navigation.DetailScreen
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import moxy.MvpPresenter

//Router необходим для навигации.
//В MvpPresenter есть экземпляр класса ViewState, который реализует тот тип View, которым
//типизирован MvpPresenter - UsersView.
//В остальном всё просто:
//● получаем данные из репозитория;
//● при первом присоединении View вызываем метод init(), в котором напишем все операции по
//инициализации View;
class UsersPresenter(
    private val model: GithubUsersRepository,
    private val router: Router
) : MvpPresenter<UsersView>() {

    private var usersFlow: Flow<PagingData<GithubUser>> = emptyFlow()
    private val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.Main
            + SupervisorJob()
    )

    /**
     *  Этот метод в первый раз вызывается при присоединении View к Presenter.
    //При уничтожении View, например, при повороте экрана, она отсоединится, а при пересоздании
    //присоединится вновь. И на новой View согласно стратегиям будут выполняться команды.
    //При повторном присоединении onFirstViewAttach уже вызываться не будет — это важно помнить.
     */
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadUsers()
    }

    private fun loadUsers() {
        viewModelCoroutineScope.launch {
            try {
                usersFlow = model.getUsersWithPagination()
                    .flow
                    .cachedIn(this)
                viewState.init(usersFlow)
            } catch (e: Exception) {
                viewState::showError
            }
        }
    }

    /**переход на экран пользователя c помощью router.navigateTo
    //при нажатии на элемент создаем объект DetailScreen и вызываем метод create,
    //который в свою очередь создает Detailfragment и ложит логин пользователя в корзину
     */
    fun onUserClicked(user: GithubUser) =
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