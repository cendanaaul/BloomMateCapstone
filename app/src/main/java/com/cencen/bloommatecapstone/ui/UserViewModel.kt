package com.cencen.bloommatecapstone.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cencen.bloommatecapstone.data.model.User
import com.cencen.bloommatecapstone.data.repository.CatalogRepository
import kotlinx.coroutines.launch
import okhttp3.RequestBody

class UserViewModel(private val rep: CatalogRepository): ViewModel() {

    fun userLogin(email: String, password: String) = rep.userLogin(email, password)
    fun userRegister(fullname: String, username:String, email: String, password: String, address: String,contact: String)
    = rep.userRegister(fullname, username, email, password, address, contact)
    fun saveUser(user: User) {
        viewModelScope.launch { rep.saveUserData(user) }
    }
    fun login() {
        viewModelScope.launch { rep.login() }
    }
    fun logout() {
        viewModelScope.launch { rep.logout() }
    }
}