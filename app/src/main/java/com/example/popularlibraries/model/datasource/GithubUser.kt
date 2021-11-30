package com.example.popularlibraries.model.datasource

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import com.google.gson.annotations.SerializedName

//Наша model - сущность, представляющая пользователя
//Загружаем список пользователей с их данными с сайта GitHub.

@Entity(
    tableName = "github_users",
    primaryKeys = ["id", "repos_url"],
    indices = [Index(value = ["repos_url"], unique = true)]
)
data class GithubUser(
    @ColumnInfo(name = "id")
    @SerializedName("id") val id: Long,
    @ColumnInfo(name = "login")
    @SerializedName("login") val login: String,
    @ColumnInfo(name = "avatar")
    @SerializedName("avatar_url") val avatarUrl: String,
    @ColumnInfo(name = "repos_url")
    @SerializedName("repos_url") val reposUrl: String
)