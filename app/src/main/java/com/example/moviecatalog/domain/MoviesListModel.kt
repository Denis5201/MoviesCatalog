package com.example.moviecatalog.domain

import kotlinx.serialization.Serializable

@Serializable
data class MoviesListModel(
    val movies: List<MovieElementModel>?
)
