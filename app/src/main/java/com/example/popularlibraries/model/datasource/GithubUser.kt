package com.example.popularlibraries.model.datasource

import com.google.gson.annotations.SerializedName

//Наша model - сущность, представляющая пользователя
//Загружаем список пользователей с их данными с сайта GitHub.

data class GithubUser(
    @SerializedName("id") val id: Long,
    @SerializedName("login") val login: String,
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("repos_url") val reposUrl: String
)