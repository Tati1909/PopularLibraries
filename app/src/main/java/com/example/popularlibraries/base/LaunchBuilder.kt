package com.example.popularlibraries.base

import android.util.Log
import androidx.annotation.CheckResult
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class LaunchBuilder(
    private val scope: CoroutineScope,
    private val block: suspend CoroutineScope.() -> Any,
    private var errorCallback: ((Throwable) -> Unit)? = null,
    private var completionCallback: (() -> Unit)? = null,
    private val logErrorResponse: (Throwable) -> Unit = {}
) {

    @CheckResult
    fun catch(errorCallback: ((Throwable) -> Unit)): LaunchBuilder = apply {
        this.errorCallback = errorCallback
    }

    @CheckResult
    fun finally(completionCallback: (() -> Unit)): LaunchBuilder = apply {
        this.completionCallback = completionCallback
    }

    fun start(isSupervisor: Boolean = false): Job = scope.launch {
        try {
            if (isSupervisor) supervisorScope(block)
            else coroutineScope(block)
        } catch (t: Throwable) {
            logErrorResponse(t)
            when {
                t is CancellationException -> Log.d("Tag", "Coroutine was cancelled")
                errorCallback != null -> errorCallback?.invoke(t)
                else -> {
                    Log.e(t.message, "Unhandled exception")
                    throw t
                }
            }
        }
    }.apply {
        invokeOnCompletion { completionCallback?.invoke() }
    }
}