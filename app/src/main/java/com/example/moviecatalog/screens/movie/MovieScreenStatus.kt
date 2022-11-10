package com.example.moviecatalog.screens.movie

import com.example.moviecatalog.domain.MovieDetailsModel

data class MovieScreenStatus(
    val isLoading: Boolean = true,
    val isStart: Boolean = true,
    val isGetProfile: Boolean = false,
    val makingRequest: Boolean = false,
    val movieDetail: MovieDetailsModel? = null,
    val isFavorite: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val userHaveReview: Boolean = false,
    val showMessage: Boolean = false,
    val textMessage: String? = null,
)
