package com.example.popularlibraries.view.details

import com.example.popularlibraries.base.BaseViewModel
import com.example.popularlibraries.model.entity.GitHubUserEntity
import com.example.popularlibraries.model.entity.GitHubUserRepoEntity
import com.example.popularlibraries.model.repository.GithubUsersRepository
import com.example.popularlibraries.navigation.InfoStarter
import com.example.popularlibraries.scheduler.Schedulers
import com.github.terrakok.cicerone.Router
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import kotlinx.coroutines.flow.MutableStateFlow

class DetailsViewModel @AssistedInject constructor(
    @Assisted private val userLogin: String,
    private val gitHubRepo: GithubUsersRepository,
    //Schedulers - наш интерфейс
    private val schedulers: Schedulers,
    private val infoStarter: InfoStarter,
    router: Router
) : BaseViewModel(router) {

    //CompositeDisposable позволяет отменять наборы цепочек
    //для операций add(Disposable), remove(Disposable) и delete(Disposable).
    private val disposables = CompositeDisposable()

    val userEntity = MutableStateFlow<GitHubUserEntity?>(null)
    val repoEntity = MutableStateFlow<List<GitHubUserRepoEntity>?>(emptyList())
    val loading = MutableStateFlow(true)
    val userNotFoundShowed = MutableStateFlow(true)
    val reposNotFoundShowed = MutableStateFlow(true)
    val error = MutableStateFlow("")

    init {
        loadUser()
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    private fun loadUser() {
        /** Параметры subscribe :
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

    private fun doOnSuccessLoadUserLoginData(user: GitHubUserEntity) {
        userEntity.value = user
        loadUserReposData(user)
    }

    private fun doOnErrorLoadUserLoginData(throwable: Throwable) {
        error.value = throwable.message ?: "Ошибка соединения с сервером. Повторите попытку позже"
    }

    private fun doOnCompleteLoadUserLoginData() {
        userNotFoundShowed.value = true
    }

    private fun loadUserReposData(user: GitHubUserEntity) {
        /**Здесь мы передаем ссылку на репозитории repositoriesUrl
        для их загрузки*/
        loading.value = true
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
        repoEntity.value = gitHubUserRepos
        loading.value = false
    }

    private fun doOnErrorLoadUserReposData(error: Throwable) {
        this.error.value = error.message ?: "Ошибка соединения с сервером. Повторите попытку позже"
        loading.value = false
    }

    private fun doOnCompleteLoadUserReposData() {
        reposNotFoundShowed.value = true
        loading.value = false
    }

    /**переход на экран с инфо о репозитории c помощью router.navigateTo
    //при нажатии на элемент создаем объект InfoScreen и вызываем метод create,
    //который в свою очередь создает InfoFragment и ложит ссылку репозитория пользователя в корзину
     */
    private fun displayUser(repoUrl: String) {
        router.navigateTo(infoStarter.info(repoUrl))
    }

    /**Для обработки нажатия клавиши «Назад» добавляем функцию backPressed(). Она возвращает
    Boolean, где мы передаём обработку выхода с экрана роутеру. Вообще, функции Presenter, согласно
    парадигме, не должны ничего возвращать, но в нашем случае приходится идти на компромисс из-за
    недостатков фреймворка.*/
    fun backPressed(): Boolean {
        router.exit()
        return true
    }

    fun onUserNotFoundShowed() {
        userNotFoundShowed.value = false
    }

    fun onReposNotFoundShowed() {
        reposNotFoundShowed.value = false
    }

    @AssistedFactory
    interface Factory {

        fun create(userLogin: String): DetailsViewModel
    }
}