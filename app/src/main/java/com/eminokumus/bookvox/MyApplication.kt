package com.eminokumus.bookvox

import android.app.Application
import com.eminokumus.bookvox.model.User

class MyApplication: Application() {

    var currentUser: User? = null
}