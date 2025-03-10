package com.eminokumus.bookvox.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eminokumus.bookvox.extensions.isValidEmail
import com.eminokumus.bookvox.extensions.isValidPassword
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LoginViewModel : ViewModel() {

    private val auth: FirebaseAuth = Firebase.auth

    private val _isEmailValid = MutableLiveData<Boolean>()
    val isEmailValid: LiveData<Boolean> get() = _isEmailValid

    private val _isPasswordValid = MutableLiveData<Boolean>()
    val isPasswordValid: LiveData<Boolean> get() = _isPasswordValid

    private val _isLoginSuccessful = MutableLiveData<Boolean>()
    val isLoginSuccessful: LiveData<Boolean> = _isLoginSuccessful

    fun checkEmailFormat(email: String?) {
        _isEmailValid.value = email.isValidEmail()
    }

    fun checkPasswordFormat(userPassword: String?) {
        _isPasswordValid.value = userPassword.isValidPassword()
    }

    fun checkFirebaseAuthentication(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            _isLoginSuccessful.value = it.isSuccessful
        }
    }

    fun isLoginAllowed(): Boolean {
        return isEmailValid.value == true && isPasswordValid.value == true
    }

    fun isThereLoggedInUser(): Boolean {
        return auth.currentUser != null
    }
}