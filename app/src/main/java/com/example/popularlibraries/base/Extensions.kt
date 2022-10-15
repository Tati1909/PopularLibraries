package com.example.popularlibraries.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

fun Fragment.repeatOnLifecycleStarted(collector: suspend () -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            collector()
        }
    }
}

fun <T> Fragment.collectWhenStarted(flow: Flow<T>, collector: suspend (T) -> Unit) {
    repeatOnLifecycleStarted { flow.collect(collector) }
}

fun Fragment.collectTrueWhenStarted(flow: Flow<Boolean>, collector: suspend (Unit) -> Unit) {
    repeatOnLifecycleStarted { flow.collectTrue(collector) }
}

fun <T> Fragment.collectNotNullWhenStarted(flow: Flow<T?>, collector: suspend (T) -> Unit) {
    repeatOnLifecycleStarted { flow.collectNotNull(collector) }
}

fun <T : CharSequence> Fragment.collectNotEmptyWhenStarted(flow: Flow<T?>, collector: suspend (T) -> Unit) {
    repeatOnLifecycleStarted { flow.collectNotEmpty(collector) }
}

fun <T> Fragment.collectNotEmptyListWhenStarted(flow: Flow<List<T>?>, collector: suspend (List<T>) -> Unit) {
    repeatOnLifecycleStarted { flow.collectNotEmptyList(collector) }
}

fun CoroutineScope.tryLaunch(
    logErrorResponse: (Throwable) -> Unit = {},
    block: suspend CoroutineScope.() -> Unit
): LaunchBuilder = LaunchBuilder(this, block, logErrorResponse = logErrorResponse)

fun <T> Flow<T>.handleErrors(handler: (t: Throwable) -> Unit = {}): Flow<T> = flow {
    try {
        catch { handler(it) }.collect { value -> emit(value) }
    } catch (e: Throwable) {
        handler(e)
    }
}

suspend fun <T> Flow<T?>.collectNotNull(collector: FlowCollector<T>) {
    collect { it?.let { collector.emit(it) } }
}

suspend fun <T : CharSequence> Flow<T?>.collectNotEmpty(collector: FlowCollector<T>) {
    collect {
        if (!it.isNullOrEmpty()) collector.emit(it)
    }
}

suspend fun <T> Flow<List<T>?>.collectNotEmptyList(collector: FlowCollector<List<T>>) {
    collect {
        if (it != null && it.isNotEmpty()) collector.emit(it)
    }
}

suspend fun Flow<Boolean?>.collectTrue(collector: FlowCollector<Unit>) {
    collect { if (it == true) collector.emit(Unit) }
}