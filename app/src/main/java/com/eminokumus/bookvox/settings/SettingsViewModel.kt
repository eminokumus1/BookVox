package com.eminokumus.bookvox.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eminokumus.bookvox.model.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class SettingsViewModel: ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth

    private val _user = MutableLiveData<User?>()
    val user: MutableLiveData<User?> = _user

    fun setUser(user: User?){
        _user.value = user
    }

    fun logout(){
        auth.signOut()
    }
}