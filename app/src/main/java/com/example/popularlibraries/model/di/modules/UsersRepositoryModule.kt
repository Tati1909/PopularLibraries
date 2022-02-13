package com.example.popularlibraries.model.di.modules

import com.example.popularlibraries.model.cloud.CloudUserDataSource
import com.example.popularlibraries.model.cloud.CloudUserDataSourceImpl
import com.example.popularlibraries.model.repository.GithubUsersRepository
import com.example.popularlibraries.model.repository.GithubUsersRepositoryImpl
import com.example.popularlibraries.model.storage.CacheUserDataSource
import com.example.popularlibraries.model.storage.CacheUserDataSourceImpl
import dagger.Binds
import dagger.Module

@Module(
    includes = [
        CacheModule::class,
        ApiModule::class
    ]
)
interface UsersRepositoryModule {

    /**
     * В параметр функции передаем конкретную имплементацию, а возвращаем реализацию интерфейса.
     * @Binds используем только в интерфейсе.
     * Binds связывает класс-имплементацию и интерфейс (GithubUsersRepositoryImpl и GithubUsersRepository)
     *
     * Удаляем фабрики и добавляем функции, предоставляющие различные объекты.
     */

    @Binds
    fun bindUsersRepository(repository: GithubUsersRepositoryImpl): GithubUsersRepository

    @Binds
    fun bindUsersCloudDataSource(dataSource: CloudUserDataSourceImpl): CloudUserDataSource

    @Binds
    fun bindUsersCacheDataSource(dataSource: CacheUserDataSourceImpl): CacheUserDataSource
}