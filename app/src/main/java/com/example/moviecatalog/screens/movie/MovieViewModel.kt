package com.example.moviecatalog.screens.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MovieViewModel: ViewModel() {
    private val _stars = MutableLiveData(mutableListOf(false, false, false, false, false, false, false, false, false, false))
    val stars: LiveData<MutableList<Boolean>> = _stars
    fun setActiveStars(index: Int) {
        val newList = MutableList(10) {false}
        for (i in 0..9) {
            newList[i] = i <= index
        }
        _stars.value = newList
        maySave()
    }

    private val _textDialogReview = MutableLiveData("")
    val textDialogReview: LiveData<String> = _textDialogReview
    fun setTextDialogReview(value: String) {
        _textDialogReview.value = value
        maySave()
    }

    private val _isAnonymous = MutableLiveData(false)
    val isAnonymous: LiveData<Boolean> = _isAnonymous
    fun changeIsAnonymous(value: Boolean) {
        _isAnonymous.value = value
    }

    private val _save = MutableLiveData(false)
    val save: LiveData<Boolean> = _save

    private val _showDialog = MutableLiveData(false)
    val showDialog: LiveData<Boolean> = _showDialog
    fun changeShowDialog(value: Boolean) {
        _showDialog.value = value
    }

    private fun maySave() {
        _save.value = _stars.value!![0] != false
    }
}