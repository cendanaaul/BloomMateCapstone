package com.cencen.bloommatecapstone.ui.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.cencen.bloommatecapstone.R
import com.cencen.bloommatecapstone.data.Libraries
import com.cencen.bloommatecapstone.databinding.ActivityRegisterBinding
import com.cencen.bloommatecapstone.ui.UserViewModel
import com.cencen.bloommatecapstone.ui.login.LoginActivity
import com.cencen.bloommatecapstone.util.ViewModelProviderFactory
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val userViewModel: UserViewModel by viewModels<UserViewModel> {
        ViewModelProviderFactory.getInstance(this)
    }
    //private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        //auth = FirebaseAuth.getInstance()

//        initialVM()
        initialAction()
    }

//    private fun initialVM() {
//        val fact: ViewModelProviderFactory = ViewModelProviderFactory.getInstance(this)
//        userViewModel = ViewModelProvider(this, fact)[UserViewModel::class.java]
//    }

    private fun initialAction() {
        binding.btnToLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.btnRegister.setOnClickListener {
//            showLoading(true)
            if (isRightInput()) {
                val fullName = binding.etFullname.text.toString()
                val userName = binding.etUsername.text.toString()
                val email = binding.etEmail.text.toString()
                val password = binding.etPassword.text.toString()
                val contact = binding.etContact.text.toString()
                val address = binding.etAdress.text.toString()

                //registerFirebase(email, password)

                if (isValidEmail(email)) {
                    userViewModel.userRegister(fullName,userName,email, password, address, contact).observe(this){
                        when(it) {
                            is Libraries.Success -> {
                                showLoading(false)
                                showToast(it.data.toString())
                                startActivity(Intent(this, LoginActivity::class.java))
                                finishAffinity()
                            }
                            is Libraries.Loading -> showLoading(true)
                            is Libraries.Error -> {
                                showToast(it.error)
                                showLoading(false)
                            }
                        }
                    }
                }
//                if (isValidEmail(email)) {
//                    val requestFullName = fullName.toRequestBody("text/plain".toMediaType())
//                    val requestUsername = userName.toRequestBody("text/plain".toMediaType())
//                    val requestEmail = email.toRequestBody("text/plain".toMediaType())
//                    val requestPassword = password.toRequestBody("text/plain".toMediaType())
//                    val requestAddress = address.toRequestBody("text/plain".toMediaType())
////                    val requestLon= lon.toRequestBody("text/plain".toMediaType())
////                    val requestLat = lat.toRequestBody("text/plain".toMediaType())
//                    val requestContact = contact.toRequestBody("text/plain".toMediaType())
//
//                    userViewModel.userRegister(requestFullName, requestUsername, requestEmail, requestPassword, requestAddress,requestContact).observe(this) {
//                        when(it) {
//                            is Libraries.Success -> {
//                                showLoading(false)
//                                showToast(it.data.toString())
//                                startActivity(Intent(this, LoginActivity::class.java))
//                                finishAffinity()
//                            }
//                            is Libraries.Loading -> showLoading(true)
//                            is Libraries.Error -> {
//                                showToast(it.error)
//                                showLoading(false)
//                            }
//                        }
//                    }
//                }


            } else {
                showToast(getString(R.string.wrong_input))
            }
        }
    }

    /*private fun registerFirebase(email: String, password:String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    showToast(getString(R.string.register_success))
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    showToast(it.exception?.message)
                }
            }
    }*/

    private fun isRightInput() : Boolean =
        binding.etEmail.error == null &&
                binding.etPassword.error == null &&
                binding.etFullname.error == null &&
                binding.etUsername.error == null &&
                binding.etContact.error == null &&
                binding.etAdress.error == null &&
                !binding.etEmail.text.isNullOrEmpty() &&
                !binding.etPassword.text.isNullOrEmpty() &&
                !binding.etFullname.text.isNullOrEmpty() &&
                !binding.etUsername.text.isNullOrEmpty() &&
                !binding.etContact.text.isNullOrEmpty() &&
                !binding.etAdress.text.isNullOrEmpty()

    private fun showLoading(isLoad: Boolean) {
        binding.loadingProcess.visibility = if (isLoad) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun isValidEmail(email: String?): Boolean {
        if (email == null) {
            return false
        }
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }
}