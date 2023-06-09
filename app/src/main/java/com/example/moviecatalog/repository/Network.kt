package com.example.moviecatalog.repository

import com.example.moviecatalog.Shared
import com.example.moviecatalog.api.*
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

        return client.build()
    }

    private fun getRetrofit(): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .client(getHttpClient())
            .build()
    }

    private val retrofit = getRetrofit()

    var token: Token? = Shared.getString("Token")?.let { Token(it) }

    fun getAuthApi(): AuthApi = retrofit.create(AuthApi::class.java)
    fun getUserApi(): UserApi = retrofit.create(UserApi::class.java)
    fun getMovieApi(): MovieApi = retrofit.create(MovieApi::class.java)
    fun getFavoriteMoviesApi(): FavoriteMoviesApi = retrofit.create(FavoriteMoviesApi::class.java)
    fun getReviewApi(): ReviewApi = retrofit.create(ReviewApi::class.java)
}