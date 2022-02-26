package com.example.popularlibraries.view.details

import com.example.popularlibraries.model.entity.GitHubUserEntity
import com.example.popularlibraries.model.entity.GitHubUserRepoEntity
import com.example.popularlibraries.model.repository.GithubUsersRepository
import com.example.popularlibraries.navigation.InfoScreen
import com.example.popularlibraries.scheduler.Schedulers
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import moxy.MvpPresenter

/**
 * @Inject constructor используем тогда, когда все аргументы берутся из графа.
 * @AssistedInject constructor - когда есть хотя бы 1 аргумент не из графа.
 *
 * @Assisted помечаем поле, которое берется не из графа (например userLogin берется из фрагмента),
 * т. е. говорим: Dagger, не смотри на это поле.
 * Чтобы применить @Assisted мы должны создать фабрику DetailPresenterFactory,
 * чтобы передать userLogin в параметры.
 */
class DetailPresenter(
    private val userLogin: String,
    private val gitHubRepo: GithubUsersRepository,
    //Schedulers - наш интерфейс
    private val schedulers: Schedulers,
    private val router: Router
) : MvpPresenter<DetailsView>() {

    //CompositeDisposable позволяет отменять наборы цепочек
    //для операций add(Disposable), remove(Disposable) и delete(Disposable).
    private val disposables = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadUser()
    }

    private fun loadUser() {
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
                        { throwable -> doOnErrorLoadUserLoginData(throwable) },
                        { doOnCompleteLoadUserLoginData() }
                    )
            )
        }
    }

    /**
     * При нажатии на репозиторий переходим на другой экран и
     * передаем ссылку на выбранный репозиторий - repoUrl
     */
    fun onItemClicked(gitHubUserRepoEntity: GitHubUserRepoEntity) {
        displayUser(gitHubUserRepoEntity.repoUrl)
    }

    fun doOnSuccessLoadUserLoginData(user: GitHubUserEntity) {
        viewState.showUser(user)
        loadUserReposData(user)
    }

    private fun doOnErrorLoadUserLoginData(throwable: Throwable) {
        viewState.showError(throwable)
    }

    private fun doOnCompleteLoadUserLoginData() {
        viewState.showUserNotFound()
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

    fun doOnCompleteLoadUserReposData() {
        viewState.showReposNotFound()
        viewState.loadingLayoutIsVisible(false)
    }

    /**переход на экран с инфо о репозитории c помощью router.navigateTo
    //при нажатии на элемент создаем объект InfoScreen и вызываем метод create,
    //который в свою очередь создает InfoFragment и ложит ссылку репозитория пользователя в корзину
     */
    fun displayUser(repoUrl: String) =
        router.navigateTo(InfoScreen(repoUrl).create())

    /**Для обработки нажатия клавиши «Назад» добавляем функцию backPressed(). Она возвращает
    Boolean, где мы передаём обработку выхода с экрана роутеру. Вообще, функции Presenter, согласно
    парадигме, не должны ничего возвращать, но в нашем случае приходится идти на компромисс из-за
    недостатков фреймворка.*/
    fun backPressed(): Boolean {
        router.exit()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }
}