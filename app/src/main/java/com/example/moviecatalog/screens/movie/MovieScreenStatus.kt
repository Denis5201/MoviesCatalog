package com.example.moviecatalog.screens.movie

import com.example.moviecatalog.domain.MovieDetailsModel

data class MovieScreenStatus(
    val isLoading: Boolean = true,
    val isStart: Boolean = true,
    val movieDetail: MovieDetailsModel? = null,
    val isError: Boolean = false,
    val errorMassage: String? = null,
    val isAddFavoriteSuccess: Boolean = false,
    val isDeleteFavoriteSuccess: Boolean = false,
    val isAddReviewSuccess: Boolean = false,
    val isDeleteReviewSuccess: Boolean = false,
    val isChangeReviewSuccess: Boolean = false
)
