package com.eminokumus.bookvox.readmode

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.eminokumus.bookvox.MainActivity
import com.eminokumus.bookvox.R
import com.eminokumus.bookvox.databinding.FragmentReadModeBinding


class ReadModeFragment : Fragment() {
    private lateinit var binding: FragmentReadModeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReadModeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
    }

    private fun setOnClickListeners(){
        setBackButtonOnClickListener()
    }

    private fun setBackButtonOnClickListener() {
        binding.backButton.setOnClickListener {
            (activity as MainActivity).displayBottomNavBar()
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                (activity as MainActivity).displayBottomNavBar()
                findNavController().popBackStack()

            }
        })
    }

}