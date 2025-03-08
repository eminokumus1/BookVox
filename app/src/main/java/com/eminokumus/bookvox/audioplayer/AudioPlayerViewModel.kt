package com.eminokumus.bookvox.audioplayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.eminokumus.bookvox.model.Book
import kotlin.text.StringBuilder

class AudioPlayerViewModel : ViewModel() {
    private val _book = MutableLiveData<Book>()
    val book: LiveData<Book> get() = _book

    private val _audioDurationInSeconds = MutableLiveData<Int>()
    val audioDurationInSeconds: LiveData<Int> get() = _audioDurationInSeconds

    val audioDurationInMinutes: LiveData<String> = _audioDurationInSeconds.map { seconds->
        millisToMinutesString(secondsToMillis(seconds))
    }

    private val _currentTimeInMinutes = MutableLiveData<String>()
    val currentTimeInMinutes: LiveData<String> get() = _currentTimeInMinutes

    private val _currentTimeInMillis = MutableLiveData<Long>()
    val currentTimeInMillis: LiveData<Long> get() = _currentTimeInMillis

    val currentTimeInSeconds: LiveData<Int> = _currentTimeInMillis.map { millis ->
        millisToSeconds(millis).toInt()
    }


    fun setBook(book: Book) {
        _book.value = book
    }

    fun setAudioDuration(durationInMillis: Long) {
        _audioDurationInSeconds.value = millisToSeconds(durationInMillis).toInt()
    }

    private fun millisToMinutesString(millis: Long): String {
        val seconds = millisToSeconds(millis)
        val minutes = seconds.div(60).toInt()
        val remainSeconds = seconds % 60
        val hours = seconds.div(3600) % 24
        if (hours > 0) {
            return String.format("%02d:%02d:%02d", hours, minutes, remainSeconds)
        } else {
            return String.format("%02d:%02d", minutes, remainSeconds)
        }

    }

    fun millisToSeconds(num: Long): Long {
        return num / 1000
    }

    fun secondsToMillis(num: Int): Long {
        return (num * 1000).toLong()
    }

    fun updateCurrentTime(millis: Long) {
        _currentTimeInMinutes.value = millisToMinutesString(millis)
        _currentTimeInMillis.value = millis
    }


}