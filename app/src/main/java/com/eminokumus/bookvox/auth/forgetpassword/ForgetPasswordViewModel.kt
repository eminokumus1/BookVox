package com.eminokumus.bookvox.auth.forgetpassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eminokumus.bookvox.extensions.isValidEmail
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class ForgetPasswordViewModel: ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth

    private val _isEmailValid = MutableLiveData<Boolean>()
    val isEmailValid: LiveData<Boolean> get() = _isEmailValid

    private val _doesResetMailSend = MutableLiveData<Boolean>()
    val doesResetMailSend: LiveData<Boolean> = _doesResetMailSend

    fun sendPasswordResetEmail(email: String) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener {
            _doesResetMailSend.value = it.isSuccessful
        }
    }

    fun checkEmailFormat(email: String) {
        _isEmailValid.value = email.isValidEmail()
    }
}