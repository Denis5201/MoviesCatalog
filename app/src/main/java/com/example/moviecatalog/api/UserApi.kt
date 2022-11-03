package com.example.moviecatalog.api

import com.example.moviecatalog.domain.ProfileModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface UserApi {

    @GET("api/account/profile")
    suspend fun getProfile(): ProfileModel

    @PUT("api/account/profile")
    suspend fun putProfile(@Body body: ProfileModel)
}