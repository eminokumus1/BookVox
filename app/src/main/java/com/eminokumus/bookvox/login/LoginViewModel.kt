package com.eminokumus.bookvox.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LoginViewModel: ViewModel() {

    private val auth: FirebaseAuth = Firebase.auth

    private val _isEmailValid = MutableLiveData<Boolean>()
    val isEmailValid: LiveData<Boolean> get() = _isEmailValid

    private val _isPasswordValid = MutableLiveData<Boolean>()
    val isPasswordValid: LiveData<Boolean> get() = _isPasswordValid

    fun checkEmailFormat(email: String){
        _isEmailValid.value = Patterns.EMAIL_ADDRESS.matcher(email).matches() && email.isNotEmpty()
    }

    fun checkPasswordFormat(userPassword: String){
        _isPasswordValid.value = userPassword.length >= 6 && userPassword.isNotEmpty()
    }

    fun isFirebaseAuthenticationSuccessful(email: String, password: String): Boolean{
        var isLoginConfirmed = false
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if(it.isSuccessful){
                isLoginConfirmed = true
            }else{
                isLoginConfirmed = false
            }
        }
        return isLoginConfirmed
    }

    fun isLoginAllowed(): Boolean{
        return isEmailValid.value == true && isPasswordValid.value == true
    }

    fun isThereLoggedInUser(): Boolean{
        return auth.currentUser != null
    }
}