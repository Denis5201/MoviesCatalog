package com.example.moviecatalog.api

import com.example.moviecatalog.domain.ProfileModel
import retrofit2.http.GET

interface UserApi {

    @GET("api/account/profile")
    suspend fun profile(): ProfileModel
}