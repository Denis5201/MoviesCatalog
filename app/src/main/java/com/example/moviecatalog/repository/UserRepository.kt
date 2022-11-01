package com.example.moviecatalog.repository

import android.util.Log
import com.example.moviecatalog.api.UserApi
import com.example.moviecatalog.domain.ProfileModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UserRepository {
    private val api: UserApi = Network.getUserApi()

    suspend fun profile(): Flow<Result<ProfileModel>> = flow{
        try {
            val profile = api.profile()
            emit(Result.success(profile))
        } catch (e: Exception) {
            Log.e("OPS Profile", e.message.toString())
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)
}