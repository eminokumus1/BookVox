package com.eminokumus.bookvox.extensions

import android.util.Patterns


fun String?.isValidEmail() = this?.let{
    Patterns.EMAIL_ADDRESS.matcher(it).matches() && it.isNotEmpty()
} ?: false

fun String?.isValidPassword() = this?.let {
    it.length >= 6 && it.isNotEmpty()
} ?: false