package com.example.popularlibraries.model.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object GithubApiFactory {

    //базовый URI для веб-службы
    private const val BASE_URL =
        "https://api.github.com"

    //создаем объект Gson
    private val gson = GsonBuilder()
        .create()

    fun create(): GithubApi =

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder()
                    /*.addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })*/
                    .build()
            )
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(GithubApi::class.java)
    /**
     * RxJava3CallAdapterFactory обеспечивает преобразование
    вызовов в источники RxJava. Именно он позволяет возвращать Single результата вызова.
    GsonConverterFactory обеспечивает преобразования ответа сервера из формата json в готовые
    объекты, в нашем случае — в экземпляр класса User.
     */
}