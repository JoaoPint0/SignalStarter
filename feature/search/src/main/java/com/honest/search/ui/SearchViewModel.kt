package com.honest.search.ui

import com.honest.search.models.SearchScreenUiState
import com.mobile.common.state.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() : BaseViewModel<SearchScreenUiState>() {

    override fun onPageRefresh(showOfflineError: Boolean) {
        load(SearchScreenUiState(emptyList()))
    }
}