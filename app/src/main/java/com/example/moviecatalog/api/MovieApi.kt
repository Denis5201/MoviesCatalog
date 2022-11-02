package com.example.moviecatalog.api

import com.example.moviecatalog.domain.MovieDetailsModel
import com.example.moviecatalog.domain.MoviesPagedListModel
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {

    @GET("api/movies/{page}")
    suspend fun page(@Path("page") page: Int): MoviesPagedListModel

    @GET("api/movies/details/{id}")
    suspend fun details(@Path("id") id: String): MovieDetailsModel
}