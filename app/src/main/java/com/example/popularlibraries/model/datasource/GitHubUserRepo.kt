package com.example.popularlibraries.model.datasource

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.google.gson.annotations.SerializedName

/**
 * Так как пользователь и репозиторий состоят в отношении «один ко многим»,
потребуется внешний ключ. Для этого мы создали поле reposUrl, которое связали с полем reposUrl в классе
GithubUser посредством аргумента аннотации. В аргумент foreignKeys передаётся массив всех
внешних ключей. Здесь он один и в него мы передаём:
1. Класс внешней сущности entity.
2. Поле repos_url, к которому привязываемся во внешней сущности GithubUser в parentColumns.
3. Поле repos_url, которое привязываем в текущей сущности в childColumns.
4. Поведение дочерних сущностей при удалении родительской в onDelete. В нашем случае мы
передаём CASCADE, чтобы дочерние сущности исчезали при удалении родительской, так как
репозитории без пользователя сюда не подходят.
 */
@Entity(
    tableName = "github_user_repo",
    foreignKeys = [ForeignKey(
        entity = GithubUser::class,
        parentColumns = ["repos_url"],
        childColumns = ["repos_url"],
        onDelete = ForeignKey.CASCADE
    )],
    primaryKeys = ["id", "repo_url"],
    indices = [Index(value = ["repo_url"], unique = true),
        Index("repos_url")]
)
data class GitHubUserRepo(
    @ColumnInfo(name = "id") @SerializedName("id") val id: Int,
    @ColumnInfo(name = "name") @SerializedName("name") val name: String,
    @ColumnInfo(name = "repo_url") @SerializedName("url") val repoUrl: String,
    @ColumnInfo(name = "repos_url") var reposUrl: String
)
