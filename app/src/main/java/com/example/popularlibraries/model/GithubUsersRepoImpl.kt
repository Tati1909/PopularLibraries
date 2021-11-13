package com.example.popularlibraries.model

import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

//репозиторий с фиктивными данными, которым будем пользоваться, пока не
//реализуем получение данных из сети:
class GithubUsersRepoImpl : GithubUserRepository {

    private val repositories = listOf(
        GithubUser("login1"),
        GithubUser("login2"),
        GithubUser("login3"),
        GithubUser("login4"),
        GithubUser("login5")
    )

    //получаем список пользователей
    override fun getUsers(): Single<List<GithubUser>> {
        return Single.just(repositories)
    }

    //получаем пользователя по Id
    //firstOrNull возвращает элемент списка, соответствующий заданному предикату, или null, если элемент не был найден.
    override fun getUserByLogin(userId: String): Maybe<GithubUser> {
        return repositories.firstOrNull { user: GithubUser -> user.login == userId }
            ?.let { user -> Maybe.just(user) }
            ?: Maybe.empty()
        //или вернет пользователя(если не null)
        //или вернет пустоту(если null)
    }
}