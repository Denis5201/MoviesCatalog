package com.example.moviecatalog.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegistrationViewModel : ViewModel() {
    private val _login = MutableLiveData("")
    val login : LiveData<String> = _login
    fun setLogin(value: String) {
        _login.value = value
        mayEnter()
    }

    private val _mail = MutableLiveData("")
    val mail : LiveData<String> = _mail
    fun setMail(value: String) {
        _mail.value = value
        mayEnter()
    }

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

    private val _confirmPassword = MutableLiveData("")
    val confirmPassword : LiveData<String> = _confirmPassword
    fun setConfirmPassword(value: String) {
        _confirmPassword.value = value
        mayEnter()
    }

    private val _date = MutableLiveData("")
    val date : LiveData<String> = _date
    fun setDate(value: String) {
        _date.value = value
        mayEnter()
    }

    private var gender = listOf(0, 1, 2)
    private val _selectGender = MutableLiveData(gender[0])
    val selectGender : LiveData<Int> = _selectGender
    fun setSelectGender(value: Int) {
        _selectGender.value = value
        mayEnter()
    }

    private val _registration = MutableLiveData(false)
    val registration : LiveData<Boolean> = _registration

    private fun mayEnter() {
        _registration.value = _login.value!!.isNotEmpty() && _mail.value!!.isNotEmpty()
                && _name.value!!.isNotEmpty() && _password.value!!.isNotEmpty()
                && _confirmPassword.value!!.isNotEmpty() && _selectGender.value != 0
    }
}