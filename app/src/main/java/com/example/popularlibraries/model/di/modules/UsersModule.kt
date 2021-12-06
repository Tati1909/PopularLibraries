package com.example.popularlibraries.model.di.modules

import com.example.popularlibraries.model.cloud.CloudUserDataSource
import com.example.popularlibraries.model.cloud.CloudUserDataSourceImpl
import com.example.popularlibraries.model.repository.GithubUsersRepository
import com.example.popularlibraries.model.repository.GithubUsersRepositoryImpl
import com.example.popularlibraries.model.storage.CacheUserDataSource
import com.example.popularlibraries.model.storage.CacheUserDataSourceImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface UsersModule {

    /**
     * @Singleton означает, что зависимость в параметре функции(например  GithubUsersRepositoryImpl)
     * будет создаваться в единственном экземпляре.
     * В параметр функции передаем конкретную имплементацию, а возвращаем реализацию интерфейса.
     * @Binds используем только в интерфейсе.
     *
     * Удаляем фабрики и добавляем функции, предоставляющие различные объекты.
     */

    @Singleton
    @Binds
    fun bindUsersRepository(repository: GithubUsersRepositoryImpl): GithubUsersRepository

    @Singleton
    @Binds
    fun bindCloudDataSource(dataSource: CloudUserDataSourceImpl): CloudUserDataSource

    @Singleton
    @Binds
    fun bindCacheDataSource(dataSource: CacheUserDataSourceImpl): CacheUserDataSource
}