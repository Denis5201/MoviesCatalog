package com.example.moviecatalog.screens.profile

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {
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

    private val _date = MutableLiveData("")
    val date: LiveData<String> = _date
    fun setDate(value: String) {
        _date.value = value
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
}