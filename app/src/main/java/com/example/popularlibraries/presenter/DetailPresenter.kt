package com.example.popularlibraries.presenter

import com.example.popularlibraries.model.GithubUser
import com.example.popularlibraries.model.GithubUserRepository
import com.example.popularlibraries.scheduler.Schedulers
import com.example.popularlibraries.view.details.DetailsView
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter

class DetailPresenter(
    private val userLogin: String,
    private val gitHubRepo: GithubUserRepository,
    //Schedulers - наш интерфейс
    private val schedulers: Schedulers
) :
    MvpPresenter<DetailsView>() {

    //CompositeDisposable позволяет отменять наборы цепочек
    //для операций add(Disposable), remove(Disposable) и delete(Disposable).
    private val disposables = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        /***
        // Параметры subscribe :
        onSuccess - чтобы принимать значение успеха от Maybe
        onError - Consumer <Throwable>, который вы разработали, чтобы принимать любые уведомления об ошибках от Maybe
        onComplete - чтобы принять уведомление о завершении от Maybe, т. е. это будет наше empty.
        В onComplete мы не попадем, потому что обработаем его здесь: .defaultIfEmpty(GithubUser(" ")) -
        если пустота, то верни нам пользователя с пустым логином*/
        disposables.add(
            gitHubRepo
                .getUserByLogin(userLogin)
                .subscribeOn(schedulers.background())
                .observeOn(schedulers.main())
                .defaultIfEmpty(GithubUser(" ", "", ""))
                .subscribe(viewState::showUser, viewState::showError)
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }
}