package com.example.moviecatalog.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    private val _name = MutableLiveData("")
    val name : LiveData<String> = _name
    fun setName(value: String) {
        _name.value = value
        mayEnter()
    }

    private val _password = MutableLiveData("")
    val password : LiveData<String> = _password
    fun setPassword(value: String) {
        _password.value = value
        mayEnter()
    }

    private val _entrance = MutableLiveData(false)
    val entrance : LiveData<Boolean> = _entrance

    private fun mayEnter() {
        _entrance.value = _name.value!!.isNotEmpty() && _password.value!!.isNotEmpty()
    }
}