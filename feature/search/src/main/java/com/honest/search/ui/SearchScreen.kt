package com.honest.search.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.honest.search.models.SearchScreenUiState


@Composable
fun SearchRoute(
    viewModel: SearchViewModel = hiltViewModel()
){
    viewModel.HandleUiState { modifier, state ->
        SearchScreen(modifier, state)
    }
}

@Composable
fun SearchScreen(
    modifier: Modifier,
    state: SearchScreenUiState
){
    Column(modifier) {
        state.results.forEach {
            Text(text = it)
        }
    }
}