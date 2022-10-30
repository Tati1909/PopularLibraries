package com.example.popularlibraries.view.users

sealed class UiState<out S> {
    class Error(val description: String) : UiState<Nothing>()
    class Content<out T>(val data: T) : UiState<T>()
    object Loading : UiState<Nothing>()
}