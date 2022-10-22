package com.example.moviecatalog.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    private val _name = MutableLiveData("")
    val name : LiveData<String> = _name

    private val _password = MutableLiveData("")
    val password : LiveData<String> = _password

    private val _entrance = MutableLiveData(false)
    val entrance : LiveData<Boolean> = _entrance

    fun setName(value: String) {
        _name.value = value
        mayEnter()
    }

    fun setPassword(value: String) {
        _password.value = value
        mayEnter()
    }

    fun mayEnter() {
        if (_name.value!!.isNotEmpty() && _password.value!!.isNotEmpty())
            _entrance.value = true
    }
}