package com.eminokumus.bookvox.bookdetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.eminokumus.bookvox.MainActivity
import com.eminokumus.bookvox.databinding.FragmentBookDetailsBinding


class BookDetailsFragment : Fragment() {
    private lateinit var binding: FragmentBookDetailsBinding
    private val viewModel: BookDetailsViewModel by viewModels()

    private val args: BookDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookDetailsBinding.inflate(layoutInflater, container, false)

        viewModel.setBook(args.book)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        setBackButtonOnClickListener()
        setPlayAudioButtonOnClickListener()
        setReadBookButtonOnClickListener()
    }

    private fun setBackButtonOnClickListener() {
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setPlayAudioButtonOnClickListener() {
        binding.playAudioButton.setOnClickListener {
            findNavController().navigate(BookDetailsFragmentDirections.actionBookDetailsFragmentToAudioPlayerFragment(args.book))
        }
    }

    private fun setReadBookButtonOnClickListener() {
        binding.readBookButton.setOnClickListener {
            findNavController().navigate(BookDetailsFragmentDirections.actionBookDetailsFragmentToReadModeFragment(args.book))
        }
    }


}