package com.eminokumus.bookvox.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doAfterTextChanged
import com.eminokumus.bookvox.MainActivity
import com.eminokumus.bookvox.R
import com.eminokumus.bookvox.databinding.ActivityLoginBinding
import com.eminokumus.bookvox.signup.SignUpActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        observeViewModel()
        checkLoggedInUser()
        checkFields()
        setOnClickListeners()
    }

    private fun observeViewModel() {
        observeEmail()
        observePassword()
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

    private fun checkFields() {
        binding.emailEditText.doAfterTextChanged {
            viewModel.checkEmailFormat(binding.emailEditText.text.toString())
        }
        binding.passwordEditText.doAfterTextChanged {
            viewModel.checkPasswordFormat(binding.passwordEditText.text.toString())
        }
    }

    private fun setOnClickListeners() {
        setLoginButtonOnClickListener()
        setForgetButtonOnClickListener()
        setRegisterTextOnClickListener()
        setRootOnClickListener()
    }


    private fun setLoginButtonOnClickListener() {
        binding.loginButton.setOnClickListener {
            if (viewModel.isLoginAllowed()) {
                checkFirebaseAuthentication()
            }
        }
    }

    private fun checkFirebaseAuthentication() {
        if (viewModel.isFirebaseAuthenticationSuccessful(
                binding.emailEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )
        ) {
            navigateToMainActivity()
        } else {
            Toast.makeText(this, "Wrong email or password", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun setForgetButtonOnClickListener() {
        // TODO("Navigate to Forget Password screen after implementing the ForgetPasswordActivity")
    }

    private fun setRegisterTextOnClickListener() {
        binding.registerText.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setRootOnClickListener() {
        binding.root.setOnClickListener {
            hideKeyboard()
        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }

    private fun checkLoggedInUser() {
        if (viewModel.isThereLoggedInUser()) {
            navigateToMainActivity()
        }
    }
}