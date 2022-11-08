package com.example.moviecatalog.repository

import android.util.Log
import com.example.moviecatalog.api.AuthApi
import com.example.moviecatalog.domain.LoginCredentialsModel
import com.example.moviecatalog.domain.Token
import com.example.moviecatalog.domain.UserRegisterModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AuthRepository {
    private val api: AuthApi = Network.getAuthApi()

    suspend fun register(body: UserRegisterModel): Flow<Result<Token>> = flow{
        try {
            val token = api.register(body)
            Network.token = token
            emit(Result.success(token))
        } catch (e: Exception) {
            Log.e("OPS register", e.message.toString())
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun login(body: LoginCredentialsModel): Flow<Result<Token>> = flow{
        try {
            val token = api.login(body)
            Network.token = token
            emit(Result.success(token))
        } catch (e: Exception) {
            Log.e("OPS login", e.message.toString())
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun logout(): Flow<Result<Unit>> = flow{
        try {
            Network.token = Token("")
            api.logout()
            emit(Result.success(Unit))
        } catch (e: Exception) {
            Log.e("OPS logout", e.message.toString())
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)
}