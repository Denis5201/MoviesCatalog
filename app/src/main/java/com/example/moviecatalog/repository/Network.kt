package com.example.moviecatalog.repository

import com.example.moviecatalog.api.AuthApi
import com.example.moviecatalog.domain.Token
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object Network {
    private const val BASE_URL = "https://react-midterm.kreosoft.space/"

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    private fun getHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder().apply {
            addInterceptor(AuthInterceptor())
            val logLevel = HttpLoggingInterceptor.Level.BODY
            addInterceptor(HttpLoggingInterceptor().setLevel(logLevel))
        }

        return  client.build()
    }

    private fun getRetrofit(): Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .client(getHttpClient())
            .build()

        return retrofit
    }

    private val retrofit = getRetrofit()

    var token: Token? = null

    fun getAuthApi(): AuthApi = retrofit.create(AuthApi::class.java)
}