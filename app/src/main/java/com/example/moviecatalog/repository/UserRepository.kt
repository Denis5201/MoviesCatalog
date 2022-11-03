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

    suspend fun getProfile(): Flow<Result<ProfileModel>> = flow{
        try {
            val profile = api.getProfile()
            emit(Result.success(profile))
        } catch (e: Exception) {
            Log.e("OPS getProfile", e.message.toString())
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun putProfile(body: ProfileModel): Flow<Result<Unit>> = flow{
        try {
            api.putProfile(body)
            emit(Result.success(Unit))
        } catch (e: Exception) {
            Log.e("OPS putProfile", e.message.toString())
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)
}