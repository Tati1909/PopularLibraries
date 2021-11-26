package com.example.popularlibraries.model.storage

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.popularlibraries.model.datasource.GithubUser
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

@Dao
interface GitHubUserDao {

    //Запрос SQLite возвращает все столбцы из github_users таблицы
    @Query("SELECT * from github_users")
    fun getUsers(): Single<List<GithubUser>>

    //SQLite для извлечения определенного элемента из таблицы элементов на основе заданного логина
    @Query("SELECT * from github_users WHERE login LIKE :login LIMIT 1")
    fun getUserByLogin(login: String): Maybe<GithubUser>

    /**Выполнение операций с базой данных может занять много времени, поэтому они должны выполняться в отдельном потоке.
    Аргумент OnConflict говорит Room, что делать в случае конфликта. OnConflictStrategy.IGNORE игнорирует новый item ,
    если этот первичный ключ уже есть в базе данных.

    Когда вы вызываете insert()из своего кода Kotlin, Room выполняет SQL-запрос для вставки объекта(item) в базу данных.*/
    @Insert(onConflict = REPLACE)
    fun insert(users: List<GithubUser>): Completable

    @Update(onConflict = REPLACE)
    fun update(user: GithubUser): Completable

    @Delete
    fun delete(user: GithubUser)
}