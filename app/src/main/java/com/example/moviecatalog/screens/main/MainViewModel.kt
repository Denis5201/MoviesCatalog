package com.example.moviecatalog.screens.main

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalog.repository.FavoriteMoviesRepository
import com.example.moviecatalog.repository.MovieRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val movieRepository = MovieRepository()
    private val favoriteMoviesRepository = FavoriteMoviesRepository()

    var state = mutableStateOf(MainScreenState())

    init {
        state.value = state.value.copy(
            isLoading = true
        )
        loadPage()
        getFavorites()
    }

    fun loadPage() {
        viewModelScope.launch {
            if (!state.value.isMakingRequest) {
                state.value = state.value.copy(
                    isMakingRequest = true
                )

                movieRepository.page(state.value.nextPage)
                    .collect { result ->
                        result.onSuccess {
                            state.value = state.value.copy(
                                items = state.value.items + it.movies!!,
                                nextPage = state.value.nextPage + 1,
                                endReached = it.movies.isEmpty(),
                                favorite = state.value.favorite
                            )
                        }.onFailure {
                            state.value = state.value.copy(
                                isError = true,
                                errorMessage = it.message,
                                nextPage = state.value.nextPage,
                                items = state.value.items,
                                favorite = state.value.favorite
                            )
                        }
                    }
                state.value = state.value.copy(
                    isMakingRequest = false
                )
            }
        }
    }

    private fun getFavorites() {
        viewModelScope.launch {
            favoriteMoviesRepository.getFavorites()
                .collect { result ->
                    result.onSuccess {
                        state.value = state.value.copy(
                            isError = true,
                            errorMessage = state.value.errorMessage,
                            nextPage = state.value.nextPage,
                            items = state.value.items,
                            favorite = it.movies ?: emptyList(),
                            isLoading = false
                        )
                    }.onFailure {
                        state.value = state.value.copy(
                            isError = true,
                            errorMessage = it.message,
                            nextPage = state.value.nextPage,
                            items = state.value.items
                        )
                    }
                }
        }
    }
}