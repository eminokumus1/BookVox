package com.eminokumus.bookvox.utils

import android.os.Build
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?){
    url?.let {
        Glide.with(view.context)
            .load(it)
            .into(view)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@BindingAdapter("dateOfBirthString")
fun formatDateOfBirth(view: TextView, date: LocalDate){
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val formattedDate = date.format(formatter)
    view.text = formattedDate
}