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

    var status = mutableStateOf(MainScreenStatus())

    init {
        status.value = status.value.copy(
            isLoading = true
        )
        loadPage()
        getFavorites()
    }

    fun loadPage() {
        viewModelScope.launch {
            if (!status.value.isMakingRequest) {
                status.value = status.value.copy(
                    isMakingRequest = true
                )

                movieRepository.page(status.value.nextPage)
                    .collect { result ->
                        result.onSuccess {
                            status.value = status.value.copy(
                                items = status.value.items + it.movies!!,
                                nextPage = status.value.nextPage + 1,
                                endReached = it.movies.isEmpty(),
                                favorite = status.value.favorite
                            )
                        }.onFailure {
                            status.value = status.value.copy(
                                isError = true,
                                errorMessage = it.message,
                                nextPage = status.value.nextPage,
                                items = status.value.items,
                                favorite = status.value.favorite
                            )
                        }
                    }
                status.value = status.value.copy(
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
                        status.value = status.value.copy(
                            isError = true,
                            errorMessage = status.value.errorMessage,
                            nextPage = status.value.nextPage,
                            items = status.value.items,
                            favorite = it.movies ?: emptyList(),
                            isLoading = false
                        )
                    }.onFailure {
                        status.value = status.value.copy(
                            isError = true,
                            errorMessage = it.message,
                            nextPage = status.value.nextPage,
                            items = status.value.items
                        )
                    }
                }
        }
    }
}