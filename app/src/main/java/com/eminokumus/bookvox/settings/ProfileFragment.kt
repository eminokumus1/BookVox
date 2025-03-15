package com.eminokumus.bookvox.settings

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.eminokumus.bookvox.MainActivity
import com.eminokumus.bookvox.MyApplication
import com.eminokumus.bookvox.databinding.FragmentProfileBinding
import com.eminokumus.bookvox.extensions.dashFormatToLocalDate
import com.eminokumus.bookvox.extensions.dotFormatToLocalDate
import com.eminokumus.bookvox.extensions.slashFormatToLocalDate
import com.eminokumus.bookvox.model.User
import java.time.LocalDate


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = ((activity as MainActivity).application as MyApplication).currentUser
        viewModel.setUser(user)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        observeViewModel()
        checkFields()
        setOnClickListeners()
    }

    private fun observeViewModel(){
        observeName()
        observeEmail()
        observeDateOfBirth()
    }

    private fun observeName() {
        viewModel.isNameValid.observe(viewLifecycleOwner) { isValid ->
            if (isValid) {
                binding.nameEditText.error = null
            } else {
                binding.nameEditText.error = "Name cannot be empty"
            }
        }
    }

    private fun observeEmail() {
        viewModel.isEmailValid.observe(viewLifecycleOwner) { isValid ->
            if (isValid) {
                binding.emailEditText.error = null
            } else {
                binding.emailEditText.error = "Wrong email format"
            }
        }
    }

    private fun observeDateOfBirth() {
        viewModel.isDateOfBirthValid.observe(viewLifecycleOwner) { isValid ->
            if (isValid) {
                binding.dateOfBirthEditText.error = null
            } else {
                binding.dateOfBirthEditText.error = "Wrong date format"
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun checkFields() {
        binding.nameEditText.doAfterTextChanged {
            viewModel.checkNameFormat(binding.nameEditText.text.toString())
        }
        binding.emailEditText.doAfterTextChanged {
            viewModel.checkEmailFormat(binding.emailEditText.text.toString())
        }
        binding.dateOfBirthEditText.doAfterTextChanged {
            viewModel.checkDateOfBirthFormat(binding.dateOfBirthEditText.text.toString())
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setOnClickListeners(){
        setSaveButtonOnClickListener()
        setBackButtonOnClickListener()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setSaveButtonOnClickListener(){
        binding.saveButton.setOnClickListener {
            if(viewModel.isSavingAllowed()){
                val dateOfBirth = dateStringToLocalDate(binding.dateOfBirthEditText.text.toString())
                val user = viewModel.user.value?.let { it ->
                    User(
                        it.userId, //When database added, user id will be generated automatically
                        binding.nameEditText.text.toString(),
                        binding.usernameEditText.text.toString(),
                        binding.emailEditText.text.toString(),
                        binding.phoneEditText.text.toString(),
                        it.profileImageUrl, // When choosing photo from gallery or camera functionality added, image url will be saved
                        dateOfBirth)
                }
                if (user != null) {
                    viewModel.saveUser(user)
                    updateApplicationUser(user)
                    Toast.makeText(requireContext(), "User saved", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
            }else{
                Toast.makeText(requireContext(), "Please fill all fields correctly", Toast.LENGTH_SHORT).show()
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun dateStringToLocalDate(date: String): LocalDate{
        return if (date.contains("/")) {
            date.slashFormatToLocalDate()
        } else if (date.contains(".")) {
            date.dotFormatToLocalDate()
        } else {
            date.dashFormatToLocalDate()
        }
    }

    private fun updateApplicationUser(user: User){
        ((activity as MainActivity).application as MyApplication).currentUser = user
    }

    private fun setBackButtonOnClickListener() {
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}