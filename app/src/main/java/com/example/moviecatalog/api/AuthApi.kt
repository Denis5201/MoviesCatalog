package com.example.moviecatalog.api

import com.example.moviecatalog.domain.Token
import com.example.moviecatalog.domain.UserRegisterModel
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("api/account/register")
    suspend fun register(@Body body: UserRegisterModel): Token
}