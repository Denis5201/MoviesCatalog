package com.example.moviecatalog.repository

import android.util.Log
import com.example.moviecatalog.api.MovieApi
import com.example.moviecatalog.domain.MovieDetailsModel
import com.example.moviecatalog.domain.MoviesPagedListModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MovieRepository {
    private val api: MovieApi = Network.getMovieApi()

    suspend fun page(numberPage: Int): Flow<Result<MoviesPagedListModel>> = flow {
        try {
            val page = api.page(numberPage)
            emit(Result.success(page))
        } catch (e: Exception) {
            Log.e("OPS page", e.message.toString())
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun details(id: String): Flow<Result<MovieDetailsModel>> = flow {
        try {
            val details = api.details(id)
            emit(Result.success(details))
        } catch (e: Exception) {
            Log.e("OPS details", e.message.toString())
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)
}