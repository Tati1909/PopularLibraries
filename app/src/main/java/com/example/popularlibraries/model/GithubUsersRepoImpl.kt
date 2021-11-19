package com.example.popularlibraries.model

import com.example.popularlibraries.model.datasource.UserDataSource
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

class GithubUsersRepoImpl(private val cloud: UserDataSource) : GithubUserRepository {

    //получаем список пользователей
    @GET("/users")
    override fun getUsers(): Single<List<GithubUser>> {
        return cloud.getUsers()
    }

    //получаем пользователя по Id
    //firstOrNull возвращает элемент списка, соответствующий заданному предикату, или null, если элемент не был найден.
    @GET("users/{login}")
    override fun getUserByLogin(userId: String): Maybe<GithubUser> {
        return cloud.getUsers()
            .flatMapMaybe { users: List<GithubUser> ->
                users
                    .firstOrNull { user: GithubUser -> user.login == userId }
                    ?.let { user -> Maybe.just(user) }
                    ?: Maybe.empty()
                //или вернет пользователя(если не null)
                //или вернет пустоту(если null)
            }
    }
}