package com.example.popularlibraries.presenter

import com.example.popularlibraries.model.entity.GitHubUserEntity
import com.example.popularlibraries.model.entity.GitHubUserRepoEntity
import com.example.popularlibraries.model.repository.GithubUsersRepository
import com.example.popularlibraries.navigation.InfoScreen
import com.example.popularlibraries.scheduler.Schedulers
import com.example.popularlibraries.view.details.DetailsView
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import moxy.MvpPresenter

class DetailPresenter(
    private val userLogin: String,
    private val gitHubRepo: GithubUsersRepository,
    //Schedulers - наш интерфейс
    private val schedulers: Schedulers,
    private val router: Router
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
        onError - получаем тост об ошибке
        onComplete - получаем тост о том, что нет выбранного пользователя */
        userLogin.let { login ->
            disposables.add(
                gitHubRepo
                    .getUserByLogin(login)
                    .map(GitHubUserEntity.Mapper::map)
                    .subscribeOn(schedulers.background())
                    .observeOn(schedulers.main())
                    .subscribe(
                        { userEntity -> doOnSuccessLoadUserLoginData(userEntity) },
                        { throwable -> viewState.showError(throwable) },
                        { viewState.showUserNotFound() }
                    )
            )
        }
    }

    private fun doOnSuccessLoadUserLoginData(user: GitHubUserEntity) {
        viewState.showUser(user)
        loadUserReposData(user)
    }

    private fun loadUserReposData(user: GitHubUserEntity) {
        /**Здесь мы передаем ссылку на репозитории repositoriesUrl
        для их загрузки*/
        viewState.loadingLayoutIsVisible(true)
        user.userReposUrl?.let { repositoriesUrl ->
            disposables += gitHubRepo.getUserRepositories(repositoriesUrl)
                .map { gitHubUserRepos -> gitHubUserRepos.map(GitHubUserRepoEntity.Mapper::map) }
                .observeOn(schedulers.main())
                .subscribeOn(schedulers.background())
                .subscribe(
                    this::doOnSuccessLoadUserReposData,
                    this::doOnErrorLoadUserReposData,
                    this::doOnCompleteLoadUserReposData
                )
        }
    }

    private fun doOnSuccessLoadUserReposData(gitHubUserRepos: List<GitHubUserRepoEntity>) {
        viewState.showRepos(gitHubUserRepos)
        viewState.loadingLayoutIsVisible(false)
    }

    private fun doOnErrorLoadUserReposData(error: Throwable) {
        viewState.showError(error)
        viewState.loadingLayoutIsVisible(false)
    }

    private fun doOnCompleteLoadUserReposData() {
        viewState.showReposNotFound()
        viewState.loadingLayoutIsVisible(false)
    }

    /**переход на экран с инфо о репозитории c помощью router.navigateTo
    //при нажатии на элемент создаем объект InfoScreen и вызываем метод create,
    //который в свою очередь создает InfoFragment и ложит ссылку репозитория пользователя в корзину
     */
    fun displayUser(repoUrl: String) =
        router.navigateTo(InfoScreen(repoUrl).create())

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }
}