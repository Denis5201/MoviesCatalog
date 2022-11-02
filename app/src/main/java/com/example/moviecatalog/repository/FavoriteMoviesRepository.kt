package com.example.moviecatalog.repository

import android.util.Log
import com.example.moviecatalog.api.FavoriteMoviesApi
import com.example.moviecatalog.domain.MoviesListModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FavoriteMoviesRepository {
    private val api: FavoriteMoviesApi = Network.getFavoriteMoviesApi()

    suspend fun getFavorites(): Flow<Result<MoviesListModel>> = flow {
        try {
            val favorites = api.getFavorites()
            emit(Result.success(favorites))
        } catch (e: Exception) {
            Log.e("OPS getFavorites", e.message.toString())
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)
}