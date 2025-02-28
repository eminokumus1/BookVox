package com.eminokumus.bookvox.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.eminokumus.bookvox.Constants
import com.eminokumus.bookvox.ScreenType
import com.eminokumus.bookvox.databinding.FragmentHomeBinding
import com.eminokumus.bookvox.model.Book


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private  val viewModel: HomeViewModel by viewModels()

    private val homeAdapter = HomeAdapter(ScreenType.HOME).also {
        it.onBookItemClickListener = object : OnBookItemClickListener{
            override fun onItemClick(book: Book) {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToBookDetailsFragment(book))
            }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.homeRecycler.adapter = homeAdapter

        observeViewModel()

    }

    private fun observeViewModel(){
        viewModel.bookList.observe(viewLifecycleOwner){
            homeAdapter.bookList = it.toMutableList()
        }
    }

}