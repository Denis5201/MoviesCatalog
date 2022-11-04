package com.example.moviecatalog.repository

import android.util.Log
import com.example.moviecatalog.api.ReviewApi
import com.example.moviecatalog.domain.ReviewShortModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ReviewRepository {
    private val api: ReviewApi = Network.getReviewApi()

    suspend fun addReview(movieId: String, body: ReviewShortModel): Flow<Result<Unit>> = flow {
        try {
            api.addReview(movieId, body)
            emit(Result.success(Unit))
        } catch (e: Exception) {
            Log.e("OPS addReview", e.message.toString())
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun putReview(movieId: String, reviewId: String, body: ReviewShortModel): Flow<Result<Unit>> = flow {
        try {
            api.putReview(movieId, reviewId ,body)
            emit(Result.success(Unit))
        } catch (e: Exception) {
            Log.e("OPS putReview", e.message.toString())
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun deleteReview(movieId: String, reviewId: String): Flow<Result<Unit>> = flow {
        try {
            api.deleteReview(movieId, reviewId)
            emit(Result.success(Unit))
        } catch (e: Exception) {
            Log.e("OPS deleteReview", e.message.toString())
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)
}