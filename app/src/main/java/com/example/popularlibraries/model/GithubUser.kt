package com.example.popularlibraries.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

//Наша model - сущность, представляющая пользователя
//Здесь мы будем хранить важные нам данные.
//Аннотация @Parcelize говорит о генерировании всего boilerplate-кода, который требуется для работы Parcelable,
//что автоматически избавляет от написания его вручную.
//Загружаем список пользователей с их данными с сайта GitHub.

@Parcelize
data class GithubUser(
    @SerializedName("id") val id: String,
    @SerializedName("login") val login: String,
    @SerializedName("avatar_url") val avatarUrl: String
) : Parcelable