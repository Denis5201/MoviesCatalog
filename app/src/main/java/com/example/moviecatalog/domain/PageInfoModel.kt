package com.example.moviecatalog.domain

import kotlinx.serialization.Serializable

@Serializable
data class PageInfoModel(
    val pageSize: Int,
    val pageCount: Int,
    val currentPage: Int
)
