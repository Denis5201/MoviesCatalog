package com.example.moviecatalog.domain

import com.example.moviecatalog.MainActivity
import com.example.moviecatalog.R

object MessageController {

    const val AUTH_SUCCESS = 1
    const val WRONG_FORMAT_MAIL = 2
    const val PASSWORDS_NOT_EQUAL = 3
    const val FAVORITE_ADD = 4
    const val FAVORITE_DELETE = 5
    const val REGISTER_SUCCESS = 6
    const val LOGOUT = 7
    const val SAVE_PROFILE_SUCCESS = 8

    fun getTextMessage(messageCode: Int): String {
        return when (messageCode) {
            AUTH_SUCCESS -> MainActivity.myResources.getString(R.string.auth_success)
            WRONG_FORMAT_MAIL ->  MainActivity.myResources.getString(R.string.wrong_format_mail)
            PASSWORDS_NOT_EQUAL ->  MainActivity.myResources.getString(R.string.passwords_not_equal)
            FAVORITE_ADD -> MainActivity.myResources.getString(R.string.favorite_add)
            FAVORITE_DELETE -> MainActivity.myResources.getString(R.string.favorite_delete)
            REGISTER_SUCCESS -> MainActivity.myResources.getString(R.string.register_success)
            LOGOUT -> MainActivity.myResources.getString(R.string.logout)
            SAVE_PROFILE_SUCCESS -> MainActivity.myResources.getString(R.string.save_profile_success)
            else -> ""
        }
    }
}