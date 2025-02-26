package com.eminokumus.bookvox.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eminokumus.bookvox.Constants
import com.eminokumus.bookvox.model.Book

class SearchViewModel : ViewModel() {

    private val _searchList = MutableLiveData<MutableList<Book>>(mutableListOf())
    val searchList: LiveData<MutableList<Book>> get() = _searchList

    fun searchInBooks(searchKeyword: String) {
        val list = mutableListOf<Book>()

        if (searchKeyword.isNotEmpty()) {
            for (item in Constants.bookList) {
                addToListIfBookListContains(item, searchKeyword, list)
                if (list.size >= 10) {
                    break
                }
            }
        }

        _searchList.value = list
    }

    private fun addToListIfBookListContains(
        item: Book,
        searchKeyword: String,
        list: MutableList<Book>
    ) {
        if (item.name?.contains(searchKeyword, ignoreCase = true) == true
            || item.author?.contains(searchKeyword, ignoreCase = true) == true
        ) {
            list.add(item)
        }
    }

}