package com.example.popularlibraries

import com.example.popularlibraries.model.datasource.GithubUser
import com.example.popularlibraries.model.entity.GitHubUserEntity
import com.example.popularlibraries.model.repository.GithubUsersRepository
import com.example.popularlibraries.scheduler.Schedulers
import com.example.popularlibraries.view.details.DetailPresenter
import com.example.popularlibraries.view.details.DetailsView
import com.github.terrakok.cicerone.Cicerone
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

/**
 * В данном случае мы тестируем только презентер
 */
class DetailPresenterTest {

    private lateinit var presenter: DetailPresenter

    private val userLogin: String = "myLogin"

    @Mock
    private lateinit var repository: GithubUsersRepository

    @Mock
    private lateinit var schedulers: Schedulers

    @Mock
    private lateinit var view: DetailsView

    private var entity = mock(GitHubUserEntity::class.java)

    private var githubUser = mock(GithubUser::class.java)

    @Before
    fun setUp() {
        //setup cicerone
        val cicerone = Cicerone.create()
        val router = cicerone.router

        /** initMocks ищет объекты с аннотацией Mock и создает заглушки  */
        MockitoAnnotations.initMocks(this)
//Создаем Презентер, используя моки, проинициализированные строкой выше
        presenter = DetailPresenter(userLogin, repository, schedulers, router)
    }

    @Test
    fun doOnCompleteLoadUserReposDataTest() {
        //вызываем функцию
        presenter.doOnCompleteLoadUserReposData()
        //проверяем вызвались ли функции мока внутри doOnCompleteLoadUserReposData
        verify(view, times(1)).showReposNotFound()
        verify(view, times(1)).loadingLayoutIsVisible(false)
        //проверяем, что функция showUserNotFound не будет вызываться внутри doOnCompleteLoadUserReposData
        verify(view, never()).showUserNotFound()
    }

    @Test
    fun doOnSuccessLoadUserLoginDataTest() {
        presenter.doOnSuccessLoadUserLoginData(entity)
        verify(view).showUser(entity)
    }
}