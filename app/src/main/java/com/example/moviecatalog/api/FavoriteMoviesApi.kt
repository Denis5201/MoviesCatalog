package com.example.moviecatalog.api

import com.example.moviecatalog.domain.MoviesListModel
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FavoriteMoviesApi {

    @GET("api/favorites")
    suspend fun getFavorites(): MoviesListModel

    @POST("api/favorites/{id}/add")
    suspend fun addFavorites(@Path("id") id: String)

    @POST("api/favorites/{id}/delete")
    suspend fun deleteFavorites(@Path("id") id: String)
}