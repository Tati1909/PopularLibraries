package com.example.popularlibraries.model.datasource

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

//Наша model - сущность, представляющая пользователя
//Загружаем список пользователей с их данными с сайта GitHub.

@Entity(tableName = "github_users")
data class GithubUser(
    @PrimaryKey
    @SerializedName("id") val id: Long,
    @ColumnInfo(name = "login")
    @SerializedName("login") val login: String,
    @ColumnInfo(name = "avatar")
    @SerializedName("avatar_url") val avatarUrl: String,
    @ColumnInfo(name = "repos")
    @SerializedName("repos_url") val reposUrl: String,
    @ColumnInfo(name = "migrate")
    val migrate: String?
)