package com.example.moviecatalog.api

import com.example.moviecatalog.domain.LoginCredentialsModel
import com.example.moviecatalog.domain.Token
import com.example.moviecatalog.domain.UserRegisterModel
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("api/account/register")
    suspend fun register(@Body body: UserRegisterModel): Token

    @POST("api/account/login")
    suspend fun login(@Body body: LoginCredentialsModel): Token

    @POST("api/account/logout")
    suspend fun logout()
}