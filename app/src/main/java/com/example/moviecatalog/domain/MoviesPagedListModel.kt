package com.example.moviecatalog.domain

import kotlinx.serialization.Serializable

@Serializable
data class MoviesPagedListModel(
    val pageInfo: PageInfoModel,
    val movies: List<MovieElementModel>?
)
