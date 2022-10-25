package com.example.moviecatalog.screens.registration

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegistrationViewModel : ViewModel() {
    private val _login = MutableLiveData("")
    val login: LiveData<String> = _login
    fun setLogin(value: String) {
        _login.value = value
        mayRegister()
    }

    private val _mail = MutableLiveData("")
    val mail: LiveData<String> = _mail
    fun setMail(value: String) {
        _mail.value = value
        mayRegister()
        isCorrectMail()
    }

    private val _name = MutableLiveData("")
    val name: LiveData<String> = _name
    fun setName(value: String) {
        _name.value = value
        mayRegister()
    }

    private val _password = MutableLiveData("")
    val password: LiveData<String> = _password
    fun setPassword(value: String) {
        _password.value = value
        mayRegister()
        isEqualPasswords()
    }

    private val _confirmPassword = MutableLiveData("")
    val confirmPassword: LiveData<String> = _confirmPassword
    fun setConfirmPassword(value: String) {
        _confirmPassword.value = value
        mayRegister()
        isEqualPasswords()
    }

    private val _date = MutableLiveData("")
    val date: LiveData<String> = _date
    fun setDate(value: String) {
        _date.value = value
        mayRegister()
    }

    private var gender = listOf(0, 1, 2)
    private val _selectGender = MutableLiveData(gender[0])
    val selectGender: LiveData<Int> = _selectGender
    fun setSelectGender(value: Int) {
        _selectGender.value = value
        mayRegister()
    }

    private val _registration = MutableLiveData(false)
    val registration: LiveData<Boolean> = _registration

    private val _correctMail = MutableLiveData(false)
    val correctMail: LiveData<Boolean> = _correctMail

    private val _equalPasswords = MutableLiveData(false)
    val equalPasswords: LiveData<Boolean> = _equalPasswords

    private fun mayRegister() {
        _registration.value = _login.value!!.isNotEmpty() && _mail.value!!.isNotEmpty()
                && _name.value!!.isNotEmpty() && _password.value!!.isNotEmpty()
                && _confirmPassword.value!!.isNotEmpty() && date.value!!.isNotEmpty()
                && _selectGender.value != 0
    }

    private fun isCorrectMail() {
        _correctMail.value = Patterns.EMAIL_ADDRESS.matcher(_mail.value!!).matches()
    }

    private fun isEqualPasswords() {
        _equalPasswords.value = _password.value == _confirmPassword.value
    }
}