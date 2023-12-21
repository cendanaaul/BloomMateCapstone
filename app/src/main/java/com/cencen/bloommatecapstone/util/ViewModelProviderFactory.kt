package com.cencen.bloommatecapstone.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cencen.bloommatecapstone.data.injection.Injection
import com.cencen.bloommatecapstone.data.repository.CatalogRepository
import com.cencen.bloommatecapstone.ui.UserViewModel
import com.cencen.bloommatecapstone.ui.cart.CartViewModel
import com.cencen.bloommatecapstone.ui.dictionary.DictionaryViewModel
import com.cencen.bloommatecapstone.ui.home.HomeViewModel
import com.cencen.bloommatecapstone.ui.maps.MapsViewModel

class ViewModelProviderFactory (private val rep: CatalogRepository):
ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(rep) as T
        }
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(rep) as T
        }
        if (modelClass.isAssignableFrom(DictionaryViewModel::class.java)) {
            return DictionaryViewModel(rep) as T
        }
        if (modelClass.isAssignableFrom(MapsViewModel::class.java)) {
            return MapsViewModel(rep) as T
        }


        throw IllegalArgumentException("Undetected vm: "+modelClass.simpleName)
    }

    companion object {
        @Volatile
        private var instance: ViewModelProviderFactory? = null
        fun getInstance(context: Context): ViewModelProviderFactory {
            return instance ?: synchronized(this) {
                instance ?: ViewModelProviderFactory(Injection.setRepository(context))
            }.also { instance = it }
        }
    }
}