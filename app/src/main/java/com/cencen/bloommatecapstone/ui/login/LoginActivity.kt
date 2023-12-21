package com.cencen.bloommatecapstone.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.cencen.bloommatecapstone.MainActivity
import com.cencen.bloommatecapstone.R
import com.cencen.bloommatecapstone.data.Libraries
import com.cencen.bloommatecapstone.data.model.User
import com.cencen.bloommatecapstone.databinding.ActivityLoginBinding
import com.cencen.bloommatecapstone.ui.UserViewModel
import com.cencen.bloommatecapstone.ui.register.RegisterActivity
import com.cencen.bloommatecapstone.util.ViewModelProviderFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        initialVM()
        initialAction()
    }

    private fun initialVM() {
        val fact: ViewModelProviderFactory = ViewModelProviderFactory.getInstance(this)
        userViewModel = ViewModelProvider(this, fact)[UserViewModel::class.java]
    }

    private fun initialAction() {
        binding.btnSignin.setOnClickListener {
            showLoading(true)
            if (isRightInput()) {
                val email = binding.etEmail.text.toString()
                val password = binding.etPassword.text.toString()
                userViewModel.userLogin(email, password).observe(this) {
                    when (it) {
                        is Libraries.Success -> {
                            showLoading(false)
                            val response = it.data
                            saveUserData(
                                User(
                                    response.credential.toString(),
                                    true
                                )
                            )
                            startActivity(Intent(this, MainActivity::class.java))
                            finishAffinity()
                        }
                        is Libraries.Loading -> showLoading(true)
                        is Libraries.Error -> {
                            showLoading(false)
                            showToast(it.error)
                        }
                    }
                }
            } else {
                showToast(getString(R.string.wrong_input))
            }
        }

        binding.btnRegisterHere.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun isRightInput() : Boolean =
        binding.etEmail.error == null &&
                binding.etPassword.error == null &&
                !binding.etEmail.text.isNullOrEmpty() &&
                !binding.etPassword.text.isNullOrEmpty()
    private fun showLoading(isLoad: Boolean) {
        binding.loadingProcess.visibility = if (isLoad) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun saveUserData(user: User) {
        userViewModel.saveUser(user)
    }
}