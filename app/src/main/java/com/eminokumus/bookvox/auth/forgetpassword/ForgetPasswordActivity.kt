package com.eminokumus.bookvox.auth.forgetpassword

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doAfterTextChanged
import com.eminokumus.bookvox.R
import com.eminokumus.bookvox.databinding.ActivityForgetPasswordBinding

class ForgetPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgetPasswordBinding
    private val viewModel: ForgetPasswordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityForgetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        observeViewModel()
        setOnClickListeners()
        checkEmailFormat()
    }

    private fun observeViewModel() {
        observeDoesResetMailSend()
        observeEmail()
    }

    private fun observeDoesResetMailSend() {
        viewModel.doesResetMailSend.observe(this) {
            if (it) {
                Toast.makeText(this, "Mail sent.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "This email is not exist.", Toast.LENGTH_SHORT).show()
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

    private fun setOnClickListeners() {
        setSubmitButtonOnClickListener()
        setCancelButtonOnClickListener()
    }

    private fun setSubmitButtonOnClickListener() {
        binding.submitButton.setOnClickListener {
            if (viewModel.isEmailValid.value == true) {
                viewModel.sendPasswordResetEmail(binding.emailEditText.text.toString())
            } else {
                Toast.makeText(this, "Please enter a valid email.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setCancelButtonOnClickListener() {
        binding.cancelButton.setOnClickListener {
            finish()
        }
    }

    private fun checkEmailFormat() {
        binding.emailEditText.doAfterTextChanged {
            viewModel.checkEmailFormat(binding.emailEditText.text.toString())
        }
    }
}