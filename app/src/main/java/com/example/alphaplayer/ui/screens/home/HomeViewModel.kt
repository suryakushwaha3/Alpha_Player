package com.example.alphaplayer.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alphaplayer.data.model.M3UItem
import com.example.alphaplayer.data.repository.M3URepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface HomeUiState {
    object Loading : HomeUiState
    data class Success(val movies: List<M3UItem>) : HomeUiState
    data class Error(val message: String) : HomeUiState
}

class HomeViewModel : ViewModel() {

    private val repository = M3URepository()

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState

    private val _movies = MutableStateFlow<List<M3UItem>>(emptyList())
    val movies: StateFlow<List<M3UItem>> = _movies

    private var currentUrl: String? = null

    fun loadPlaylist(url: String, forceRefresh: Boolean = false) {
        // Same URL and already loaded data check
        if (!forceRefresh && currentUrl == url && _movies.value.isNotEmpty()) {
            return
        }

        currentUrl = url
        _uiState.value = HomeUiState.Loading

        // Force refresh par stale data reset karein
        if (forceRefresh) {
            _movies.value = emptyList()
        }

        viewModelScope.launch {
            try {
                // M3URepository network execution internally Dispatchers.IO par karta hai
                val result = repository.loadPlaylistWithCache(url, forceRefresh = forceRefresh)

                if (result.isNotEmpty()) {
                    _movies.value = result
                    _uiState.value = HomeUiState.Success(result)
                } else {
                    _movies.value = emptyList()
                    _uiState.value = HomeUiState.Error("No content found or failed to parse playlist.")
                }
            } catch (e: Exception) {
                _movies.value = emptyList()
                _uiState.value = HomeUiState.Error(e.localizedMessage ?: "Failed to load data")
            }
        }
    }

    fun refresh(url: String) {
        loadPlaylist(url, forceRefresh = true)
    }
}

