package com.eminokumus.bookvox.extensions

import android.os.Build
import android.util.Patterns
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter


fun String?.isValidEmail() = this?.let{
    Patterns.EMAIL_ADDRESS.matcher(it).matches() && it.isNotEmpty()
} ?: false

fun String?.isValidPassword() = this?.let {
    it.length >= 6 && it.isNotEmpty()
} ?: false

@RequiresApi(Build.VERSION_CODES.O)
fun String.checkDateFormatWithSlash(): Boolean {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val localDate = LocalDate.parse(this, formatter)
    if (!formatter.format(localDate)
            .equals(this) || localDate > LocalDate.now()
    ) {
        return false
    }
    return true
}

@RequiresApi(Build.VERSION_CODES.O)
fun String.checkDateFormatWithDot(): Boolean {
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    val localDate = LocalDate.parse(this, formatter)
    if (!formatter.format(localDate)
            .equals(this) || localDate > LocalDate.now()
    ) {
        return false
    }
    return true
}

@RequiresApi(Build.VERSION_CODES.O)
fun String.checkDateFormatWithDash(): Boolean {
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    val localDate = LocalDate.parse(this, formatter)
    if (!formatter.format(localDate)
            .equals(this) || localDate > LocalDate.now()
    ) {
        return false
    }
    return true
}

@RequiresApi(Build.VERSION_CODES.O)
fun String.slashFormatToLocalDate(): LocalDate{
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    return LocalDate.parse(this, formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
fun String.dotFormatToLocalDate(): LocalDate{
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    return LocalDate.parse(this, formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
fun String.dashFormatToLocalDate(): LocalDate{
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    return LocalDate.parse(this, formatter)
}