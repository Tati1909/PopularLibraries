package com.example.popularlibraries.view.users

import com.example.popularlibraries.model.datasource.GithubUser
import com.example.popularlibraries.model.repository.GithubUsersRepository
import com.example.popularlibraries.navigation.DetailScreen
import com.example.popularlibraries.scheduler.Schedulers
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter

//Router необходим для навигации.
//В остальном всё просто:
//● получаем данные из репозитория;
//● при первом присоединении View вызываем метод init(), в котором напишем все операции по
//инициализации View;
class UsersPresenter(
    //Schedulers - наш интерфейс
    private val schedulers: Schedulers,
    private val model: GithubUsersRepository,
    private val router: Router
) : MvpPresenter<UsersView>() {

    //CompositeDisposable позволяет отменять наборы цепочек
    //для операций add(Disposable), remove(Disposable) и delete(Disposable).
    private val disposables = CompositeDisposable()

    //Этот метод в первый раз вызывается при присоединении View к Presenter.
    //При уничтожении View, например, при повороте экрана, она отсоединится, а при пересоздании
    //присоединится вновь. И на новой View согласно стратегиям будут выполняться команды.
    //При повторном присоединении onFirstViewAttach уже вызываться не будет — это важно помнить.
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        //Параметры: onSuccess and onError
        disposables.add(
            model
                .getUsers()
                .subscribeOn(schedulers.background())
                .observeOn(schedulers.main())
                .subscribe(
                    viewState::init,
                    viewState::showError
                )
        )
    }

    /**переход на экран пользователя c помощью router.navigateTo
    //при нажатии на элемент создаем объект DetailScreen и вызываем метод create,
    //который в свою очередь создает Detailfragment и ложит логин пользователя в корзину
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

    fun onItemClicked(user: GithubUser) {
        displayUser(user)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }
}