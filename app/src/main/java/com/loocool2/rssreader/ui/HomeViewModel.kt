package com.loocool2.rssreader.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loocool2.rssreader.data.remote.RssClient
import com.loocool2.rssreader.data.remote.ktorClient
import com.loocool2.rssreader.utils.UiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {

    /* todo: install dependency injection lib */
    private val rssClient = RssClient(ktorClient)
    private val uiMapper = HomeUiMapper()

    private val _uiState = MutableStateFlow<UiResult<HomeUiState>>(UiResult.Loading)

    val uiState: StateFlow<UiResult<HomeUiState>> = _uiState.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        UiResult.Loading
    )

    init {
        loadArticles()
    }

    private fun loadArticles() {
        viewModelScope.launch {
            try {
                val home = withContext(Dispatchers.IO) {
                    val response = rssClient.getArticles("https://www.animenewsnetwork.com/all/rss.xml?ann-edition=us")
                    uiMapper.map(response)
                }
                _uiState.update {
                    UiResult.Success(home)
                }
            } catch (err: Throwable) {
                _uiState.update { UiResult.Fail(err) }
            }
        }
    }
}