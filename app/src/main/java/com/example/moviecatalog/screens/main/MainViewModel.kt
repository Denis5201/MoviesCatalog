package com.example.moviecatalog.screens.main

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalog.domain.MessageController
import com.example.moviecatalog.domain.MovieElementModel
import com.example.moviecatalog.repository.FavoriteMoviesRepository
import com.example.moviecatalog.repository.MovieRepository
import com.example.moviecatalog.screens.FavoriteUpdateParameter
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val movieRepository = MovieRepository()
    private val favoriteMoviesRepository = FavoriteMoviesRepository()

    var status = mutableStateOf(MainScreenStatus())
    private val _favorites = MutableLiveData<MutableList<MovieElementModel>>()
    val favorites: LiveData<MutableList<MovieElementModel>> = _favorites

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
                                endReached = it.movies.isEmpty()
                            )
                        }.onFailure {
                            status.value = status.value.copy(
                                isError = true,
                                errorMessage = it.message,
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
                            isLoading = false
                        )
                        _favorites.value = (it.movies ?: mutableListOf()) as MutableList<MovieElementModel>
                    }.onFailure {
                        status.value = status.value.copy(
                            isError = true,
                            errorMessage = it.message
                        )
                    }
                }
        }
    }

    fun deleteFavorite(movieId: String) {
        viewModelScope.launch {
            favoriteMoviesRepository.deleteFavorites(movieId)
                .collect { result ->
                    result.onSuccess {
                        val temp = _favorites.value!!.toMutableList()
                        temp.removeIf { it.id == movieId }
                        _favorites.value = temp
                        status.value = status.value.copy(
                            showMessage = true,
                            textMessage = MessageController.getTextMessage(MessageController.FAVORITE_DELETE)
                        )
                    }.onFailure {
                        status.value = status.value.copy(
                            isError = true,
                            errorMessage = it.message
                        )
                    }
                }
        }
    }

    fun changeFavoriteFromMovieScreen() {
        FavoriteUpdateParameter.isFavouriteChange = false
        getFavorites()
    }

    fun setDefaultStatus() {
        status.value = status.value.copy(
            isError = false,
            showMessage = false
        )
    }
}