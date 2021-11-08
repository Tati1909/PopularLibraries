package com.example.popularlibraries.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

//Наша model - сущность, представляющая пользователя
//Здесь мы будем хранить важные нам данные.
//Аннотация @Parcelize говорит о генерировании всего boilerplate-кода, который требуется для работы Parcelable,
//что автоматически избавляет от написания его вручную.

@Parcelize
data class GithubUser(
    val login: String
) : Parcelable