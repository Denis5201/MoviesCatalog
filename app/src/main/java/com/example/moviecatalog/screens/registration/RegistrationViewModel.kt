package com.example.moviecatalog.screens.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalog.Shared
import com.example.moviecatalog.domain.MessageController
import com.example.moviecatalog.domain.UserRegisterModel
import com.example.moviecatalog.domain.Validator
import com.example.moviecatalog.repository.AuthRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

class RegistrationViewModel : ViewModel() {
    private val authRepository = AuthRepository()

    val status = MutableLiveData(RegistrationScreenStatus())

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
    }

    private val _confirmPassword = MutableLiveData("")
    val confirmPassword: LiveData<String> = _confirmPassword
    fun setConfirmPassword(value: String) {
        _confirmPassword.value = value
        mayRegister()
    }

    private val dateToFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    private var dateForServer: String = ""

    private val _date = MutableLiveData("")
    val date: LiveData<String> = _date
    fun setDate(value: Date) {
        _date.value = dateToFormat.format(value)
        dateForServer = DateTimeFormatter.ISO_INSTANT.format(value.toInstant()).toString()
        mayRegister()
    }

    private var gender = listOf(0, 1, 2)
    private val _selectGender = MutableLiveData(gender[2])
    val selectGender: LiveData<Int> = _selectGender
    fun setSelectGender(value: Int) {
        _selectGender.value = value
        mayRegister()
    }

    private val _start = MutableLiveData(true)
    val start: LiveData<Boolean> = _start
    fun setStart(value: Boolean) {
        _start.value = value
    }

    private val _registration = MutableLiveData(false)
    val registration: LiveData<Boolean> = _registration

    private fun mayRegister() {
        _registration.value = _login.value!!.isNotEmpty() && _mail.value!!.isNotEmpty()
                && _name.value!!.isNotEmpty() && _password.value!!.isNotEmpty()
                && _confirmPassword.value!!.isNotEmpty() && date.value!!.isNotEmpty()
                && _selectGender.value != 2
    }

    fun getRegisterRequest() {
        if (!Validator.isCorrectMail(_mail.value!!)) {
            status.value = status.value!!.copy(
                showMessage = true,
                textMessage = MessageController.getTextMessage(MessageController.WRONG_FORMAT_MAIL)
            )
            _registration.value = false
            return
        }
        if (Validator.isPasswordShort(_password.value!!)) {
            status.value = status.value!!.copy(
                showMessage = true,
                textMessage = MessageController.getTextMessage(MessageController.PASSWORD_SHORT)
            )
            _registration.value = false
            return
        }
        if (!Validator.isEqualPasswords(_password.value!!, _confirmPassword.value!!)) {
            status.value = status.value!!.copy(
                showMessage = true,
                textMessage = MessageController.getTextMessage(MessageController.PASSWORDS_NOT_EQUAL)
            )
            _registration.value = false
            return
        }
        viewModelScope.launch {
            val registerBody = UserRegisterModel(
                _login.value!!,
                _name.value!!,
                _password.value!!,
                _mail.value!!,
                dateForServer,
                _selectGender.value!!
            )
            authRepository.register(registerBody)
                .collect { result ->
                    result.onSuccess {
                        Shared.setString(Shared.TOKEN, it.token)
                        status.value = status.value!!.copy(
                            mayGoToMain = true,
                            showMessage = true,
                            textMessage = MessageController.getTextMessage(MessageController.REGISTER_SUCCESS)
                        )
                    }.onFailure {
                        status.value = status.value!!.copy(
                            isError = true,
                            errorMessage = it.message
                        )
                    }
                }
        }
    }

    fun setDefaultStatus() {
        status.value = RegistrationScreenStatus()
    }
}