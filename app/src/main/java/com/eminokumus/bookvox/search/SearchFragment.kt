package com.eminokumus.bookvox.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import com.eminokumus.bookvox.R
import com.eminokumus.bookvox.ScreenType
import com.eminokumus.bookvox.databinding.FragmentSearchBinding
import com.eminokumus.bookvox.home.HomeAdapter


class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: SearchViewModel

    private val homeAdapter = HomeAdapter(ScreenType.SEARCH)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        viewModel = SearchViewModel()


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
        watchSearchEditText()

        binding.searchRecycler.adapter = homeAdapter
    }

    private fun observeViewModel() {
        viewModel.searchList.observe(viewLifecycleOwner) { list ->
            homeAdapter.bookList = list
        }
    }

    private fun watchSearchEditText() {
        binding.searchEditText.doAfterTextChanged {
            viewModel.searchInBooks(binding.searchEditText.text.toString())
        }
    }
}