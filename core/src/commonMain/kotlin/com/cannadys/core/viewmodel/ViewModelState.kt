package org.example.core.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlin.coroutines.CoroutineContext

abstract class ViewModelState<STATE, ACTION>(private val defaultUiState: STATE) : ViewModel() {

    private val _state = MutableStateFlow(defaultUiState)
    val state: StateFlow<STATE> get() = _state


    val viewModelScope = object : CoroutineScope {
        override val coroutineContext: CoroutineContext
            get() = SupervisorJob() + Dispatchers.Main
    }

    protected fun updateState(block: STATE.() -> STATE) {
        _state.update(block)
    }

    abstract fun sendAction(action: ACTION)

    fun currentState() = _state.value

    fun restartState() {
        _state.update { defaultUiState }
    }

}