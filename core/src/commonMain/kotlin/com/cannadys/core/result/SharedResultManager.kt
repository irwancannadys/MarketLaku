package org.example.core.result

import com.cannadys.core.result.SharedResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * For communicate by [SharedResult] for each composable
 * */
class SharedResultManager {

    private val _sharedResult: MutableStateFlow<MutableMap<String, SharedResult>> =
        MutableStateFlow(
            mutableMapOf()
        )

    fun setResult(id: String, sharedResult: SharedResult) {
        _sharedResult.update { map ->
            map.also {
                it[id] = sharedResult
            }
        }
    }

    fun addResultObserver(
        id: String,
        scope: CoroutineScope,
        block: (SharedResult) -> Unit
    ) = scope.launch {
        _sharedResult
            .filter { it.keys.contains(id) }
            .stateIn(this)
            .mapNotNull { it.values.lastOrNull() }
            .collectLatest {
                block.invoke(it)
            }
    }
}