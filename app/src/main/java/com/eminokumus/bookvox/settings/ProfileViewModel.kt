package com.eminokumus.bookvox.settings

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eminokumus.bookvox.Constants
import com.eminokumus.bookvox.extensions.checkDateFormatWithDash
import com.eminokumus.bookvox.extensions.checkDateFormatWithDot
import com.eminokumus.bookvox.extensions.checkDateFormatWithSlash
import com.eminokumus.bookvox.extensions.isValidEmail
import com.eminokumus.bookvox.model.User

class ProfileViewModel: ViewModel() {
    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> = _user

    private val _isNameValid = MutableLiveData<Boolean>()
    val isNameValid: LiveData<Boolean> get() = _isNameValid

    private val _isEmailValid = MutableLiveData<Boolean>()
    val isEmailValid: LiveData<Boolean> get() = _isEmailValid

    private val _isDateOfBirthValid = MutableLiveData<Boolean>()
    val isDateOfBirthValid: LiveData<Boolean> get() = _isDateOfBirthValid

    fun setUser(user: User?){
        _user.value = user
    }

    fun checkNameFormat(name: String){
        _isNameValid.value = name.isNotEmpty()
    }

    fun checkEmailFormat(email: String) {
        _isEmailValid.value = email.isValidEmail()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun checkDateOfBirthFormat(dateOfBirth: String) {
        _isDateOfBirthValid.value = isDateOfBirthValid(dateOfBirth)
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

    fun isSavingAllowed(): Boolean{
        return isNameValid.value == true && isEmailValid.value == true && isDateOfBirthValid.value == true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveUser(user: User){
        _user.value = user

        // save user to database when database added
        Constants.dummyUser = user
    }
}