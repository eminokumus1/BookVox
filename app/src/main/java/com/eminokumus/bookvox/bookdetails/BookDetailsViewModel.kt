package com.eminokumus.bookvox.bookdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eminokumus.bookvox.model.Book

class BookDetailsViewModel: ViewModel() {

    private val _book = MutableLiveData<Book>()
    val book: LiveData<Book> get() = _book

    fun setBook(book: Book){
        _book.value = book
    }
}