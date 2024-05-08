package com.mobile.common.state

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed class BaseUiState<out T> {

    object Loading : BaseUiState<Nothing>()

    data class Error(val throwable: Throwable?) : BaseUiState<Nothing>()

    data class Loaded<T>(
        val data: T
    ) : BaseUiState<T>()

    @Composable
    fun HandleUiState(
        modifier: Modifier,
        content: @Composable (Modifier, T) -> Unit,
    ) {
        when (this) {
            is Error -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    ErrorState("Network error", this@BaseUiState.throwable?.message.orEmpty())
                }
            }

            is Loaded -> content(modifier, this.data)
            Loading -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    LoadingState()
                }
            }
        }
    }
}


fun <T, W> Flow<T>.asResult(mapper: (T) -> W): Flow<BaseUiState<W>> {
    return this
        .map<T, BaseUiState<W>> {
            BaseUiState.Loaded(mapper(it))
        }
        .onStart { emit(BaseUiState.Loading) }
        .catch {
            emit(BaseUiState.Error(it))
        }
}