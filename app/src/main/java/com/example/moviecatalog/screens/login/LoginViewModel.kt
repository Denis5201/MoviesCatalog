package com.example.moviecatalog.screens.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalog.domain.LoginCredentialsModel
import com.example.moviecatalog.repository.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val authRepository = AuthRepository()

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

    private val _mayGoToMain = MutableLiveData(false)
    val mayGoToMain: LiveData<Boolean> = _mayGoToMain
    fun setMayGoToMain(value: Boolean) {
        _mayGoToMain.value = value
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
                        _mayGoToMain.value = true
                    }.onFailure {

                    }
                }
        }
    }
}