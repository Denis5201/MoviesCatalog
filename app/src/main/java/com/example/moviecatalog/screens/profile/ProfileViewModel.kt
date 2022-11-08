package com.example.moviecatalog.screens.profile

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalog.Shared
import com.example.moviecatalog.domain.MessageController
import com.example.moviecatalog.domain.ProfileModel
import com.example.moviecatalog.repository.AuthRepository
import com.example.moviecatalog.repository.UserRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.*

class ProfileViewModel : ViewModel() {
    private val userRepository = UserRepository()
    private val authRepository = AuthRepository()

    val status = MutableLiveData(ProfileScreenStatus())
    private var profile: ProfileModel? = null

    private val _mail = MutableLiveData("")
    val mail: LiveData<String> = _mail
    fun setMail(value: String) {
        _mail.value = value
        maySave()
        isCorrectMail()
    }

    private val _avatar = MutableLiveData("")
    val avatar: LiveData<String> = _avatar
    fun setAvatar(value: String) {
        _avatar.value = value
        maySave()
    }

    private val _name = MutableLiveData("")
    val name: LiveData<String> = _name
    fun setName(value: String) {
        _name.value = value
        maySave()
    }

    private val _nickName = MutableLiveData("")
    val nickName: LiveData<String> = _nickName

    private val dateToFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    private var dateForServer:String = ""

    private val _date = MutableLiveData("")
    val date: LiveData<String> = _date
    fun setDate(value: Date) {
        _date.value = dateToFormat.format(value)
        dateForServer = DateTimeFormatter.ISO_INSTANT.format(value.toInstant()).toString()
        maySave()
    }

    private var gender = listOf(0, 1, 2)
    private val _selectGender = MutableLiveData(gender[2])
    val selectGender: LiveData<Int> = _selectGender
    fun setSelectGender(value: Int) {
        _selectGender.value = value
        maySave()
    }

    private val _save = MutableLiveData(false)
    val save: LiveData<Boolean> = _save

    private val _correctMail = MutableLiveData(false)

    private fun maySave() {
        _save.value = _mail.value!!.isNotEmpty() && _name.value!!.isNotEmpty()
                && date.value!!.isNotEmpty() && selectGender.value != 2
    }

    private fun isCorrectMail() {
        _correctMail.value = Patterns.EMAIL_ADDRESS.matcher(_mail.value!!).matches()
    }

    init {
        status.value = status.value?.copy(
            isLoading = true
        )
        getProfileRequest()
    }

    private fun getProfileRequest() {
        viewModelScope.launch {
            userRepository.getProfile()
                .collect { result ->
                    result.onSuccess {
                        profile = it
                        _nickName.value = it.nickName
                        _mail.value = it.email
                        _avatar.value = it.avatarLink ?: ""
                        _name.value = it.name

                        if (it.birthDate.last() != 'Z') it.birthDate += 'Z'
                        _date.value = dateToFormat.format(Date.from(Instant.parse(it.birthDate)))
                        dateForServer = it.birthDate

                        _selectGender.value = it.gender
                        setDefaultStatus()
                    }.onFailure {
                        status.value = status.value?.copy(
                            isError = true,
                            errorMassage = it.message
                        )
                    }
                }
        }
    }

    fun putProfile() {
        if (!_correctMail.value!!) {
            status.value =  status.value!!.copy(
                showMessage = true,
                textMessage = MessageController.getTextMessage(MessageController.WRONG_FORMAT_MAIL)
            )
            return
        }

        viewModelScope.launch {
            val profileBody = ProfileModel(
                profile!!.id,
                _nickName.value!!,
                _mail.value!!,
                _avatar.value,
                _name.value!!,
                dateForServer,
                _selectGender.value!!
            )
            userRepository.putProfile(profileBody)
                .collect { result ->
                    result.onSuccess {
                        status.value = status.value!!.copy(
                            showMessage = true,
                            textMessage = MessageController.getTextMessage(MessageController.SAVE_PROFILE_SUCCESS)
                        )
                    }.onFailure {
                        status.value = status.value!!.copy(
                            isError = true,
                            errorMassage = it.message
                        )
                    }
                }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
                .collect { result ->
                    result.onSuccess {
                        Shared.setString(Shared.TOKEN, "not")
                        status.value = status.value!!.copy(
                            showMessage = true,
                            textMessage = MessageController.getTextMessage(MessageController.LOGOUT),
                            logout = true
                        )
                    }.onFailure {
                        Shared.setString(Shared.TOKEN, "not")
                        status.value = status.value!!.copy(
                            showMessage = true,
                            textMessage = MessageController.getTextMessage(MessageController.LOGOUT),
                            logout = true
                        )
                    }
                }
        }
    }

    fun setDefaultStatus() {
        status.value = status.value?.copy(
            isLoading = false,
            isError = false,
            showMessage = false,
            logout = false
        )
    }
}