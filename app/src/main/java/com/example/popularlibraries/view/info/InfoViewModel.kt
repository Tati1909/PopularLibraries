package com.example.popularlibraries.view.info

import androidx.lifecycle.ViewModel
import com.example.popularlibraries.model.entity.GitHubUserRepoInfoEntity
import com.example.popularlibraries.model.repository.GithubUsersRepository
import com.example.popularlibraries.scheduler.Schedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import kotlinx.coroutines.flow.MutableStateFlow

class InfoViewModel(
    private val gitHubUsersRepository: GithubUsersRepository,
    private val repositoryUrl: String?,
    private val schedulers: Schedulers,
) : ViewModel() {

    private val disposables = CompositeDisposable()
    val loading = MutableStateFlow(true)
    val repositoryInfo = MutableStateFlow<GitHubUserRepoInfoEntity?>(null)
    val repositoryNotFoundShowed = MutableStateFlow(false)
    val error = MutableStateFlow("")

    init {
        loadData()
    }

    private fun loadData() {
        repositoryUrl?.let {
            disposables +=
                gitHubUsersRepository.getUserRepositoryInfo(it)
                    .map(GitHubUserRepoInfoEntity.Mapper::map)
                    .observeOn(schedulers.main())
                    .subscribeOn(schedulers.background())
                    .subscribe { infoEntity ->
                        repositoryInfo.value = infoEntity
                        error.value = "Ошибка соединения с сервером. Повторите попытку позже"
                        repositoryNotFoundShowed.value = true
                    }
        }
        loading.value = false
    }

    fun onRepositoryNotFoundShowed() {
        repositoryNotFoundShowed.value = false
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }
}