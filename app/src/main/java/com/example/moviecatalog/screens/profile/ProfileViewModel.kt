package com.example.moviecatalog.screens.profile

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalog.repository.UserRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.*

class ProfileViewModel : ViewModel() {
    private val userRepository = UserRepository()

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

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading
    fun setLoading(value: Boolean) {
        _loading.value = value
    }

    private fun maySave() {
        _save.value = _mail.value!!.isNotEmpty() && _name.value!!.isNotEmpty()
                && date.value!!.isNotEmpty() && selectGender.value != 2
    }

    private fun isCorrectMail() {
        _correctMail.value = Patterns.EMAIL_ADDRESS.matcher(_mail.value!!).matches()
    }

    fun getProfileRequest() {
        viewModelScope.launch {
            userRepository.profile()
                .collect() { result ->
                    result.onSuccess {
                        _mail.value = it.email
                        _avatar.value = it.avatarLink ?: ""
                        _name.value = it.name

                        if (it.birthDate.last() != 'Z') it.birthDate += 'Z'
                        _date.value = dateToFormat.format(Date.from(Instant.parse(it.birthDate)))

                        _selectGender.value = it.gender
                    }.onFailure {

                    }
                }
        }
    }
}