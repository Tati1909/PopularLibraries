package com.example.popularlibraries.model.storage.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.example.popularlibraries.model.datasource.GithubUser

/**
 * У нас есть отдельная зависимость в gradle,
 * которая поддерживает RX в Room, т.е. мы также можем указывать стримы в функциях Dao
 */
@Dao
interface GitHubUserDao {

    //Запрос SQLite возвращает все столбцы из github_users таблицы
    @Query("SELECT * from github_users")
    suspend fun getUsers(): List<GithubUser>

    //SQLite для извлечения определенного элемента из таблицы элементов на основе заданного логина
    @Query("SELECT * from github_users WHERE login LIKE :login LIMIT 1")
    suspend fun getUserByLogin(login: String): GithubUser

    /**Выполнение операций с базой данных может занять много времени, поэтому они должны выполняться в отдельном потоке.
    Аргумент OnConflict говорит Room, что делать в случае конфликта. OnConflictStrategy.IGNORE игнорирует новый item ,
    если этот первичный ключ уже есть в базе данных.

    Когда вы вызываете insert()из своего кода Kotlin, Room выполняет SQL-запрос для вставки объекта(users) в базу данных.*/
    @Insert(onConflict = REPLACE)
    suspend fun insertUsers(users: List<GithubUser>)

    @Insert(onConflict = REPLACE)
    suspend fun insertUser(user: GithubUser)

    @Update(onConflict = REPLACE)
    suspend fun update(user: GithubUser)

    @Delete
    suspend fun delete(user: GithubUser)
}