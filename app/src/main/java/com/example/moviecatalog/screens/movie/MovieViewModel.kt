package com.example.moviecatalog.screens.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalog.repository.FavoriteMoviesRepository
import com.example.moviecatalog.repository.MovieRepository
import kotlinx.coroutines.launch

class MovieViewModel: ViewModel() {
    private val movieRepository = MovieRepository()
    private val favoriteMoviesRepository = FavoriteMoviesRepository()

    private var movieId = ""
    private var _favoriteInStart = MutableLiveData(false)
    val favoriteInStart: LiveData<Boolean> = _favoriteInStart

    val status = MutableLiveData(MovieScreenStatus())

    private val _stars = MutableLiveData(mutableListOf(false, false, false, false, false, false, false, false, false, false))
    val stars: LiveData<MutableList<Boolean>> = _stars
    fun setActiveStars(index: Int) {
        val newList = MutableList(10) {false}
        for (i in 0..9) {
            newList[i] = i <= index
        }
        _stars.value = newList
        maySave()
    }

    private val _textDialogReview = MutableLiveData("")
    val textDialogReview: LiveData<String> = _textDialogReview
    fun setTextDialogReview(value: String) {
        _textDialogReview.value = value
        maySave()
    }

    private val _isAnonymous = MutableLiveData(false)
    val isAnonymous: LiveData<Boolean> = _isAnonymous
    fun changeIsAnonymous(value: Boolean) {
        _isAnonymous.value = value
    }

    private val _save = MutableLiveData(false)
    val save: LiveData<Boolean> = _save

    private val _showDialog = MutableLiveData(false)
    val showDialog: LiveData<Boolean> = _showDialog
    fun changeShowDialog(value: Boolean) {
        _showDialog.value = value
    }

    private val _heartEnable = MutableLiveData(true)
    val heartEnable: LiveData<Boolean> = _heartEnable

    private fun maySave() {
        _save.value = _stars.value!![0] != false
    }

    fun initialScreen(id: String) {
        status.value = status.value!!.copy(isStart = false)
        movieId = id
        getDetailsRequest()
    }

    private fun getDetailsRequest() {
        viewModelScope.launch {
            favoriteMoviesRepository.getFavorites()
                .collect { result ->
                    result.onSuccess {
                        it.movies?.forEach { favorite ->
                            if (favorite.id == movieId) {
                                status.value = status.value!!.copy(
                                    isFavorite = true
                                )
                                _favoriteInStart.value = status.value!!.isFavorite
                            }
                        }
                    }.onFailure {
                        status.value = status.value!!.copy(
                            isError = true,
                            errorMessage = it.message
                        )
                    }
                }
            movieRepository.details(movieId)
                .collect { result ->
                    result.onSuccess {
                       status.value = status.value!!.copy(
                           isLoading = false,
                           movieDetail = it
                       )
                    }.onFailure {
                        status.value = status.value!!.copy(
                            isError = true,
                            errorMessage = it.message
                        )
                    }
                }
        }
    }

    fun changeFavorite() {
        _heartEnable.value = false
        viewModelScope.launch {
            if (status.value!!.isFavorite) {
                favoriteMoviesRepository.deleteFavorites(movieId)
                    .collect { result ->
                        result.onSuccess {
                            status.value = status.value!!.copy(
                                isFavorite = !status.value!!.isFavorite
                            )
                            _heartEnable.value = true
                        }.onFailure {
                            status.value = status.value!!.copy(
                                isError = true,
                                errorMessage = it.message
                            )
                            _heartEnable.value = true
                        }
                    }
            } else {
                favoriteMoviesRepository.addFavorites(movieId)
                    .collect { result ->
                        result.onSuccess {
                            status.value = status.value!!.copy(
                                isFavorite = !status.value!!.isFavorite
                            )
                            _heartEnable.value = true
                        }.onFailure {
                            status.value = status.value!!.copy(
                                isError = true,
                                errorMessage = it.message
                            )
                            _heartEnable.value = true
                        }
                    }
            }
        }
    }
}