package com.cencen.bloommatecapstone.ui.cart

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.cencen.bloommatecapstone.data.Libraries
import com.cencen.bloommatecapstone.data.call.AddCartRequest
import com.cencen.bloommatecapstone.data.injection.Injection
import com.cencen.bloommatecapstone.data.model.CartItem
import com.cencen.bloommatecapstone.data.repository.CartRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class CartViewModel(private val repository: CartRepository) : ViewModel() {

    suspend fun getCarts() = repository.getCart()

    fun deleteProduct(productId: String) = repository.deleteProduct(productId)

    fun addItemToCart(addCartRequest: AddCartRequest) =
        repository.addCart(addCartRequest)

}

class CartViewModelFactory(private val rep: CartRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            return CartViewModel(rep) as T
        }

        throw IllegalArgumentException("Undetected vm: " + modelClass.simpleName)
    }

    companion object {
        @Volatile
        private var instance: CartViewModelFactory? = null
        fun getInstance(context: Context): CartViewModelFactory {
            return instance ?: synchronized(this) {
                instance ?: CartViewModelFactory(Injection.provideCartRepository(context))
            }.also { instance = it }
        }
    }
}

private fun <E> List<E>.add(element: String) {

}
