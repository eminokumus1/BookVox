package com.eminokumus.bookvox.signup

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doAfterTextChanged
import com.eminokumus.bookvox.R
import com.eminokumus.bookvox.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        observeViewModel()
        checkFields()
        setOnClickListeners()
    }

    private fun observeViewModel() {
        observeEmail()
        observePassword()
        observeDateOfBirth()
        observeDoesUserCreated()
    }

    private fun observePassword() {
        viewModel.isPasswordValid.observe(this) { isValid ->
            if (isValid) {
                binding.passwordEditText.error = null
            } else {
                binding.passwordEditText.error = "Minimum 6 characters"
            }
        }
    }

    private fun observeEmail() {
        viewModel.isEmailValid.observe(this) { isValid ->
            if (isValid) {
                binding.emailEditText.error = null
            } else {
                binding.emailEditText.error = "Wrong email format"
            }
        }
    }

    private fun observeDateOfBirth() {
        viewModel.isDateOfBirthValid.observe(this) { isValid ->
            if (isValid) {
                binding.dateOfBirthEditText.error = null
            } else {
                binding.dateOfBirthEditText.error = "Wrong date format"
            }
        }
    }

    private fun observeDoesUserCreated() {
        viewModel.doesUserCreated.observe(this) {
            if (it) {
                Toast.makeText(this, "User created", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Email is already in use", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun checkFields() {
        binding.emailEditText.doAfterTextChanged {
            viewModel.checkEmailFormat(binding.emailEditText.text.toString())
        }
        binding.passwordEditText.doAfterTextChanged {
            viewModel.checkPasswordFormat(binding.passwordEditText.text.toString())
        }
        binding.dateOfBirthEditText.doAfterTextChanged {
            viewModel.checkDateOfBirthFormat(binding.dateOfBirthEditText.text.toString())
        }
    }

    private fun setOnClickListeners() {
        setRegisterButtonOnClickListener()
        setBackToLoginButtonOnClickListener()
    }

    private fun setRegisterButtonOnClickListener() {
        binding.registerButton.setOnClickListener {
            if (viewModel.isRegisterAllowed()) {
                viewModel.createUser(
                    binding.emailEditText.text.toString(),
                    binding.passwordEditText.text.toString()
                )
            } else {
                Toast.makeText(this, "Please fill informations correctly!", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }


    private fun setBackToLoginButtonOnClickListener() {
        binding.backToLoginButton.setOnClickListener {
            finish()
        }

    }
}