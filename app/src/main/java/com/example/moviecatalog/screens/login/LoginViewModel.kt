package com.example.moviecatalog.screens.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalog.Shared
import com.example.moviecatalog.domain.LoginCredentialsModel
import com.example.moviecatalog.domain.MessageController
import com.example.moviecatalog.repository.AuthRepository
import com.example.moviecatalog.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val authRepository = AuthRepository()
    private val userRepository = UserRepository()

    val status = MutableLiveData(LoginScreenStatus())

    private val _name = MutableLiveData("")
    val name: LiveData<String> = _name
    fun setName(value: String) {
        _name.value = value
        mayEnter()
    }

    private val _password = MutableLiveData("")
    val password: LiveData<String> = _password
    fun setPassword(value: String) {
        _password.value = value
        mayEnter()
    }

    private val _entrance = MutableLiveData(false)
    val entrance: LiveData<Boolean> = _entrance

    init {
        alreadyEnter()
    }

    private fun mayEnter() {
        _entrance.value = _name.value!!.isNotEmpty() && _password.value!!.isNotEmpty()
    }

    fun getLoginRequest() {
        viewModelScope.launch {
            val loginBody = LoginCredentialsModel(
                _name.value!!,
                _password.value!!
            )
            authRepository.login(loginBody)
                .collect { result ->
                    result.onSuccess {
                        Shared.setString(Shared.TOKEN, it.token)
                        status.value = status.value!!.copy(
                            mayGoToMain = true,
                            showMessage = true,
                            textMessage = MessageController.getTextMessage(MessageController.AUTH_SUCCESS)
                        )
                        _name.value = ""
                        _password.value = ""
                        _entrance.value = false
                    }.onFailure {
                        status.value = status.value!!.copy(
                            isError = true,
                            errorMessage = if (it.message?.contains(Regex("400")) == true) {
                                _entrance.value = false
                                MessageController.getTextMessage(MessageController.INVALID_LOGIN_PASSWORD)
                            } else {
                                it.message
                            }
                        )
                    }
                }
        }
    }

    private fun alreadyEnter() {
        viewModelScope.launch {
            userRepository.getProfile()
                .collect { result ->
                    result.onSuccess {
                        status.value = status.value!!.copy(
                            mayGoToMain = true,
                            showMessage = true,
                            textMessage = MessageController.getTextMessage(MessageController.AUTH_SUCCESS)
                        )
                    }.onFailure {

                    }
                }
        }
    }

    fun setDefaultStatus() {
        status.value = LoginScreenStatus()
    }
}