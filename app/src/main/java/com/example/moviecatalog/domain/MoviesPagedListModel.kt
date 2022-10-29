package com.example.moviecatalog.domain

data class MoviesPagedListModel(
    val movies: List<MovieElementModel>?,
    val PageInfo: PageInfoModel
)
