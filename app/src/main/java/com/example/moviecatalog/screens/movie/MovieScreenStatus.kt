package com.example.moviecatalog.screens.movie

import com.example.moviecatalog.domain.MovieDetailsModel

data class MovieScreenStatus(
    val isLoading: Boolean = true,
    val isStart: Boolean = true,
    val movieDetail: MovieDetailsModel? = null,
    val isFavorite: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val userHaveReview: Boolean = false,
    val isAddFavoriteSuccess: Boolean = false,
    val isDeleteFavoriteSuccess: Boolean = false,
    val isAddReviewSuccess: Boolean = false,
    val isDeleteReviewSuccess: Boolean = false,
    val isChangeReviewSuccess: Boolean = false
)
