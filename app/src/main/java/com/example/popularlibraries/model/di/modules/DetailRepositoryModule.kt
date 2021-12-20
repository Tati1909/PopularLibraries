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
interface DetailRepositoryModule {

    /**
     * В параметр функции передаем конкретную имплементацию, а возвращаем реализацию интерфейса.
     * @Binds используем только в интерфейсе.
     *
     * Удаляем фабрики и добавляем функции, предоставляющие различные объекты.
     */

    @Binds
    fun bindUserRepository(repository: GithubUsersRepositoryImpl): GithubUsersRepository

    @Binds
    fun bindUserCloudDataSource(dataSource: CloudUserDataSourceImpl): CloudUserDataSource

    @Binds
    fun bindUserCacheDataSource(dataSource: CacheUserDataSourceImpl): CacheUserDataSource
}