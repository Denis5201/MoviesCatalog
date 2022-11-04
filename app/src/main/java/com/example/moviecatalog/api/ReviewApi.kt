package com.example.moviecatalog.api

import com.example.moviecatalog.domain.ReviewShortModel
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ReviewApi {

    @POST("api/movie/{movieId}/review/add")
    suspend fun addReview(@Path("movieId") movieId: String, body: ReviewShortModel)

    @PUT("api/movie/{movieId}/review/{id}/edit")
    suspend fun putReview(@Path("movieId") movieId: String, @Path("id") id: String, body: ReviewShortModel)

    @DELETE("api/movie/{movieId}/review/{id}/delete")
    suspend fun deleteReview(@Path("movieId") movieId: String, @Path("id") id: String)
}