package com.eminokumus.bookvox.audioplayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eminokumus.bookvox.model.Book
import kotlin.text.StringBuilder

class AudioPlayerViewModel: ViewModel() {
    private val _book = MutableLiveData<Book>()
    val book: LiveData<Book> get() = _book

    private val _audioDurationInMinutes = MutableLiveData<String>()
    val audioDurationInMinutes: LiveData<String> get() = _audioDurationInMinutes

    private val _currentTimeInMinutes = MutableLiveData<String>()
    val currentTimeInMinutes: LiveData<String> get() = _currentTimeInMinutes

    fun setBook(book: Book){
        _book.value = book
    }

    fun setAudioDuration(durationInMillis: Long){
        _audioDurationInMinutes.value = millisToMinutesString(durationInMillis)
    }

    private fun millisToMinutesString(millis: Long): String{
        val seconds = millisToSeconds(millis)
        val minutes = seconds.div(60).toInt()
        val remainSeconds = seconds%60
        val minutesInString = StringBuilder().append(minutes).append(":").append(remainSeconds).toString()
        return minutesInString
    }

    fun millisToSeconds(num: Long): Long{
        return num/1000
    }

    fun updateCurrentTime(millis: Long){
        _currentTimeInMinutes.value = millisToMinutesString(millis)
    }
}