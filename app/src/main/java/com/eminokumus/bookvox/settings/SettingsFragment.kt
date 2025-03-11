package com.eminokumus.bookvox.settings

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.eminokumus.bookvox.MainActivity
import com.eminokumus.bookvox.MyApplication
import com.eminokumus.bookvox.R
import com.eminokumus.bookvox.auth.login.LoginActivity
import com.eminokumus.bookvox.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = ((activity as MainActivity).application as MyApplication).currentUser
        viewModel.setUser(user)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        setBackButtonOnClickListener()
        setViewProfileButtonOnClickListener()
        setLogoutButtonOnClickListener()
    }

    private fun setBackButtonOnClickListener() {
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setViewProfileButtonOnClickListener() {
        binding.viewProfileButtonText.setOnClickListener {
            findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToProfileFragment())
        }
    }

    private fun setLogoutButtonOnClickListener(){
        binding.logoutButton.setOnClickListener {
            viewModel.logout()
            navigateLoginActivityWithClearBackStack()
        }

    }

    private fun navigateLoginActivityWithClearBackStack() {
        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

}