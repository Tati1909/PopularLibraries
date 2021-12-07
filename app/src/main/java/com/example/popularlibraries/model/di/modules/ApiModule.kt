package com.example.popularlibraries.model.di.modules

import com.example.popularlibraries.model.api.GithubApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

/**
 * @Provides  используем в классе,
 * @Binds используем в интерфейсе
 */
@Module
class ApiModule {

    /**
     *   У Dagger есть встроенный спецификатор Named, который позволяет дать строковое имя тому, что
    предоставляет функция fun provideApi.
     *   "https://api.github.com" - базовый URI для веб-службы
     */
    @Named("github_api")
    @Provides
    fun provideBaseUrl(): String = "https://api.github.com"

    /**
     * создаем объект Gson
     */
    @Singleton
    @Provides
    fun gson(): Gson = GsonBuilder()
        .create()

    @Singleton
    @Provides
    fun provideApi(@Named("github_api") baseUrl: String, gson: Gson): GithubApi =

        Retrofit.Builder()
            .baseUrl(baseUrl)
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