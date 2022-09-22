package com.example.popularlibraries.view.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.popularlibraries.model.datasource.GithubUser
import com.example.popularlibraries.model.repository.GithubUsersPagingSource
import com.example.popularlibraries.model.repository.GithubUsersRepository
import com.example.popularlibraries.model.repository.USER_PAGE_SIZE
import com.example.popularlibraries.navigation.DetailScreen
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch

class UsersViewModel(
    private val usersRepository: GithubUsersRepository,
    private val router: Router
) : ViewModel() {

    var users: Flow<PagingData<GithubUser>> =
        Pager(config = PagingConfig(pageSize = USER_PAGE_SIZE)) {
            GithubUsersPagingSource(usersRepository)
        }
            .flow
            .cachedIn(viewModelScope)
            .catch { throwable ->
                state.value = UiState.Error(throwable.message ?: "Ошибка сервера. Мы уже работаем над ее исправлением")
            }
    var state = MutableStateFlow<UiState<Flow<PagingData<GithubUser>>>>(UiState.Content(users))

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