package org.example.core.network

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

sealed class Async <out T> {
    data object Idle : Async<Nothing>()
    data object Loading : Async<Nothing>()
    data class Success<T>(val data: T) : Async<T>()
    data class Failure(val throwable: Throwable) : Async<Nothing>()
}

fun <T> Async<T>.isLoading(): Boolean {
    return this is Async.Loading
}

fun <T> Async<T>.isIdle(): Boolean {
    return this is Async.Idle
}

fun <T> Async<T>.getOrNull(): T? {
    return if (this is Async.Success) {
        data
    } else {
        null
    }
}

@Composable
fun <T> Async<T>.onLoading(content: @Composable () -> Unit) {
    if (this is Async.Loading) content.invoke()
}

@Composable
fun <T> Async<T>.onIdle(content: @Composable () -> Unit) {
    if (this is Async.Idle) content.invoke()
}

@Composable
fun <T> Async<T>.onSuccess(content: @Composable (T) -> Unit) {
    if (this is Async.Success) content.invoke(data)
}

@Composable
fun <T> Async<T>.onFailure(content: @Composable (Throwable) -> Unit) {
    if (this is Async.Failure) content.invoke(throwable)
}