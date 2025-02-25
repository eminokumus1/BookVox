package com.eminokumus.bookvox.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eminokumus.bookvox.Constants
import com.eminokumus.bookvox.model.Book

class HomeViewModel: ViewModel() {

    private val _bookList = MutableLiveData<List<Book>>()
    val bookList: LiveData<List<Book>> get() =  _bookList

    init {
        _bookList.value = Constants.bookList
    }
}