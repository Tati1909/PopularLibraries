package com.example.popularlibraries.model.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.popularlibraries.model.datasource.GithubUser
import kotlinx.coroutines.coroutineScope

private const val FIRST_PAGE = 1
const val USER_PAGE_SIZE = 5

class GithubUsersPagingSource(
    private val githubUsersRepository: GithubUsersRepository
) : PagingSource<Int, GithubUser>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubUser> {
        return try {
            coroutineScope {
                val currentPage = params.key ?: FIRST_PAGE
                val itemsRange = currentPage.until(currentPage + params.loadSize)
                val users = githubUsersRepository.getUsers(
                    page = currentPage,
                    count = USER_PAGE_SIZE
                )
                LoadResult.Page(
                    data = users,
                    prevKey = when (currentPage) {
                        FIRST_PAGE -> null
                        else -> ensureValidKey(key = itemsRange.first - params.loadSize)
                    },
                    nextKey = if (users.size < USER_PAGE_SIZE) null else currentPage + 1
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GithubUser>): Int? {
        val anchorPos = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPos)
        return page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
    }

    private fun ensureValidKey(key: Int): Int = Integer.max(FIRST_PAGE, key)
}