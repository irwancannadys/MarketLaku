package com.cannadys.core.result

sealed class SharedResult {
    data class Ok(val key: String? = null) : SharedResult()
    data object Cancel : SharedResult()
}