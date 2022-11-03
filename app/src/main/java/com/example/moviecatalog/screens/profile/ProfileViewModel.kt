package com.example.moviecatalog.screens.profile

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalog.domain.ProfileModel
import com.example.moviecatalog.repository.UserRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.*

class ProfileViewModel : ViewModel() {
    private val userRepository = UserRepository()
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
    val correctMail: LiveData<Boolean> = _correctMail

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
        viewModelScope.launch {
            val profileBody = ProfileModel(
                profile!!.id,
                profile!!.nickName,
                _mail.value!!,
                _avatar.value,
                _name.value!!,
                dateForServer,
                _selectGender.value!!
            )
            userRepository.putProfile(profileBody)
                .collect { result ->
                    result.onSuccess {
                        status.value = status.value?.copy(
                            isSaveSuccess = true
                        )
                    }.onFailure {
                        status.value = status.value?.copy(
                            isError = true,
                            errorMassage = it.message
                        )
                    }
                }
        }
    }

    fun setDefaultStatus() {
        status.value = status.value?.copy(
            isLoading = false,
            isError = false,
            isSaveSuccess = false
        )
    }
}