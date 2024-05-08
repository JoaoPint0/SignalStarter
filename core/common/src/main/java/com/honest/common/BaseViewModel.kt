package com.mobile.common.state

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update


abstract class BaseViewModel<T> : ViewModel() {

    private val message = MutableStateFlow<(String)?>(null)

    val state: StateFlow<BaseUiState<T>>
        get() = _state

    protected var _state = MutableStateFlow<BaseUiState<T>>(BaseUiState.Loading)

    protected fun sendMessage(new: Throwable) {
        message.update { new.message }
    }

    private fun clearMessage() {
        message.update { null }
    }

    protected fun cache() = (state.value as? BaseUiState.Loaded)?.data

    protected fun isLoading() = state.value is BaseUiState.Loading

    abstract fun onPageRefresh(showOfflineError: Boolean = true)

    protected inline fun <W> Result<W>.onResult(mapper: (W) -> T) {
        this.onSuccess { result ->
            _state.update { BaseUiState.Loaded(mapper(result)) }
        }.onFailure { throwable ->
            error(throwable)
        }
    }

    protected fun load(transform: T) {
        _state.update { BaseUiState.Loaded(transform) }
    }

    protected fun error(throwable: Throwable) {
        _state.update { BaseUiState.Error(throwable) }
    }

    protected fun onLoaded(transform: (T) -> T) {
        _state.update { if (it is BaseUiState.Loaded) it.copy(data = transform(it.data)) else it }
    }

    @Composable
    fun HandleUiState(
        floatingActionButton: @Composable () -> Unit = {},
        floatingActionButtonPosition: FabPosition = FabPosition.End,
        content: @Composable (Modifier, T) -> Unit,
    ) {
        val state by state.collectAsStateWithLifecycle()
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            floatingActionButton = floatingActionButton,
            floatingActionButtonPosition = floatingActionButtonPosition
        ) {

            Column(
                Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {

                state.HandleUiState(
                    modifier = Modifier.fillMaxSize(),
                    content = content
                )
            }
        }
    }
}