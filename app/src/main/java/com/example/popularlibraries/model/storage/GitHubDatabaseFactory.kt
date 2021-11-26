package com.example.popularlibraries.model.storage

import android.content.Context
import androidx.room.Room

object GitHubDatabaseFactory {

    /**При использовании GitHubStorageDatabase класса вы хотите убедиться, что существует только один экземпляр базы данных,
    чтобы предотвратить состояние гонки или другие потенциальные проблемы. Экземпляр создается через фабрику*/
    fun create(context: Context): GitHubDatabase =
        Room.databaseBuilder(
            context,
            GitHubDatabase::class.java,
            "github.db"
        )
            //Миграция выходит за рамки этой лаборатории. Простое решение -
            // уничтожить и восстановить базу данных, что означает потерю данных.
            .fallbackToDestructiveMigration()
            .build()
}