package com.example.popularlibraries.model.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.popularlibraries.model.datasource.GithubUser

/**
 * Нам нужно указать Room что делать с классами DAO и Entity.
// Вот где на помощь приходит класс ItemRoomDatabase. Android-приложение, использующее Room создает подклассы
// и выполняет несколько ключевых функций. В нашем приложении необходимо:

//Указать, какие entities определены в базе данных(GithubUser как единственный класс - entities = [GithubUser::class]) .
//Предоставить доступ к одному экземпляру каждого класса DAO.
//Выполнить любую дополнительную настройку, например предварительное заполнение базы данных.
//Установите exportSchema значение false, чтобы не сохранять резервные копии истории версий схемы.
 */
@Database(entities = [GithubUser::class], version = 1, exportSchema = false)
abstract class GitHubDatabase : RoomDatabase() {

    abstract fun gitHubUserDao(): GitHubUserDao
}