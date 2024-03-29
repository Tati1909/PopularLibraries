package com.example.popularlibraries.model.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.example.popularlibraries.model.datasource.GitHubUserRepo

@Dao
interface GitHubUserRepoDao {

    //получаем список репозиториев
    @Query("SELECT * from github_user_repo  WHERE repos_url = :repositoriesUrl")
    suspend fun getUserRepositories(repositoriesUrl: String): List<GitHubUserRepo>

    /**Выполнение операций с базой данных может занять много времени, поэтому они должны выполняться в отдельном потоке.
    Аргумент OnConflict говорит Room, что делать в случае конфликта. OnConflictStrategy.IGNORE игнорирует новый item ,
    если этот первичный ключ уже есть в базе данных.

    Когда вы вызываете insert()из своего кода Kotlin, Room выполняет SQL-запрос для вставки объекта(item) в базу данных.*/
    @Insert(onConflict = REPLACE)
    suspend fun insert(users: List<GitHubUserRepo>)

    @Update(onConflict = REPLACE)
    suspend fun update(user: GitHubUserRepo)
}