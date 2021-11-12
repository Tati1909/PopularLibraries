package com.example.popularlibraries.model

//репозиторий с фиктивными данными, которым будем пользоваться, пока не
//реализуем получение данных из сети:
class GithubUsersRepo : GithubUserRepository {

    private val repositories = listOf(
        GithubUser("login1"),
        GithubUser("login2"),
        GithubUser("login3"),
        GithubUser("login4"),
        GithubUser("login5")
    )

    //получаем список пользователей
    override fun getUsers(): List<GithubUser> {
        return repositories
    }

    //получаем пользователя по Id
    //firstOrNull возвращает элемент списка, соответствующий заданному предикату, или null, если элемент не был найден.

    override fun getUserByLogin(userId: String): GithubUser? =
        repositories.firstOrNull { user: GithubUser -> user.login == userId }
}