package com.example.popularlibraries.base

import android.util.Log
import androidx.annotation.CheckResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.CoroutineScope

open class BaseViewModel constructor(
    protected val router: Router
) : ViewModel() {

    @CheckResult
    protected fun tryLaunch(
        scope: CoroutineScope = viewModelScope,
        block: suspend CoroutineScope.() -> Unit,
    ): LaunchBuilder = scope.tryLaunch(::logErrorResponse, block)

    protected fun logErrorResponse(it: Throwable) {
        Log.e(it.message, this::class.java.simpleName)
    }
}