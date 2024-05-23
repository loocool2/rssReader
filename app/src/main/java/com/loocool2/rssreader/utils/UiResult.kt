package com.loocool2.rssreader.utils

sealed interface UiResult<out T> {
    data object Loading : UiResult<Nothing>
    data class Success<T>(val data: T) : UiResult<T>
    data class Fail(val error: Throwable) : UiResult<Nothing>
}