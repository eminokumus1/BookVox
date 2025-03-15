package com.eminokumus.bookvox.auth.signup

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eminokumus.bookvox.extensions.checkDateFormatWithDash
import com.eminokumus.bookvox.extensions.checkDateFormatWithDot
import com.eminokumus.bookvox.extensions.checkDateFormatWithSlash
import com.eminokumus.bookvox.extensions.isValidEmail
import com.eminokumus.bookvox.extensions.isValidPassword
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class SignUpViewModel : ViewModel() {

    private val auth: FirebaseAuth = Firebase.auth


    private val _isEmailValid = MutableLiveData<Boolean>()
    val isEmailValid: LiveData<Boolean> get() = _isEmailValid

    private val _isPasswordValid = MutableLiveData<Boolean>()
    val isPasswordValid: LiveData<Boolean> get() = _isPasswordValid

    private val _isDateOfBirthValid = MutableLiveData<Boolean>()
    val isDateOfBirthValid: LiveData<Boolean> get() = _isDateOfBirthValid

    private val _doesUserCreated = MutableLiveData<Boolean>()
    val doesUserCreated: LiveData<Boolean> = _doesUserCreated

    fun checkEmailFormat(email: String) {
        _isEmailValid.value = email.isValidEmail()
    }

    fun checkPasswordFormat(userPassword: String) {
        _isPasswordValid.value = userPassword.isValidPassword()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun checkDateOfBirthFormat(dateOfBirth: String) {
        _isDateOfBirthValid.value = isDateOfBirthValid(dateOfBirth)
    }

    fun createUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                auth.signOut()
                _doesUserCreated.value = true
            } else {
                _doesUserCreated.value = false
            }
        }
    }

    fun isRegisterAllowed(): Boolean {
        return isEmailValid.value == true
                && isPasswordValid.value == true
                && isDateOfBirthValid.value == true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun isDateOfBirthValid(date: String): Boolean {
        try {
            if (date.contains("/")) {
                return date.checkDateFormatWithSlash()
            } else if (date.contains(".")) {
                return date.checkDateFormatWithDot()
            } else if (date.contains("-")) {
                return date.checkDateFormatWithDash()
            }
            return false
        } catch (e: Exception) {
            return false
        }
    }


}