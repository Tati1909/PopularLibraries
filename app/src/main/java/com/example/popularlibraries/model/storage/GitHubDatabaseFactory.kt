package com.example.popularlibraries.model.storage

import android.content.Context
import androidx.room.Room
import com.example.popularlibraries.model.storage.migration.GitHub1to2Migration

object GitHubDatabaseFactory {

    /**При использовании GitHubStorageDatabase класса вы хотите убедиться, что существует только один экземпляр базы данных,
    чтобы предотвратить состояние гонки или другие потенциальные проблемы. Экземпляр создается через фабрику*/
    fun create(context: Context): GitHubDatabase =
        Room.databaseBuilder(
            context,
            GitHubDatabase::class.java,
            "github.db"
        )
            .addMigrations(
                GitHub1to2Migration
            )
            .build()
}