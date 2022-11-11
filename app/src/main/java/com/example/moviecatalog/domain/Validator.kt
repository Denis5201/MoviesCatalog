package com.example.moviecatalog.domain

import android.util.Patterns

object Validator {

    fun isCorrectMail(mail: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(mail).matches()
    }

    fun isPasswordShort(password: String): Boolean {
        return password.length < 6
    }

    fun isEqualPasswords(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }
}