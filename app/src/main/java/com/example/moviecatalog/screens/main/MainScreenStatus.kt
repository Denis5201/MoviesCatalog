package com.example.moviecatalog.screens.main

import com.example.moviecatalog.domain.MovieElementModel

data class MainScreenStatus(
    val isLoading: Boolean = false,
    var isMakingRequest: Boolean = false,
    val items: List<MovieElementModel> = emptyList(),
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val endReached: Boolean = false,
    val nextPage: Int = 1,
    val favorite: List<MovieElementModel> = emptyList()
)
