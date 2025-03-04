package com.eminokumus.bookvox.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.eminokumus.bookvox.R
import com.eminokumus.bookvox.ScreenType
import com.eminokumus.bookvox.databinding.FragmentSearchBinding
import com.eminokumus.bookvox.home.HomeAdapter
import com.eminokumus.bookvox.home.OnBookItemClickListener
import com.eminokumus.bookvox.model.Book


class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private  val viewModel: SearchViewModel by viewModels()

    private val homeAdapter = HomeAdapter(ScreenType.SEARCH).also {
        it.onBookItemClickListener = object : OnBookItemClickListener {
            override fun onItemClick(book: Book) {
                findNavController().navigate(
                    SearchFragmentDirections.actionSearchFragmentToBookDetailsFragment(
                        book
                    )
                )
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)

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