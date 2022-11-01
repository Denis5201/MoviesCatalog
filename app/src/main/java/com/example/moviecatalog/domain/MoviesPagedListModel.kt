package com.example.moviecatalog.domain

import kotlinx.serialization.Serializable

@Serializable
data class MoviesPagedListModel(
    val movies: List<MovieElementModel>?,
    val PageInfo: PageInfoModel
)
