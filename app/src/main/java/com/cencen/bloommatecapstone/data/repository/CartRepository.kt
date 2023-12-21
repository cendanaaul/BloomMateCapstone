package com.cencen.bloommatecapstone.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.cencen.bloommatecapstone.data.Libraries
import com.cencen.bloommatecapstone.data.api.ApiServices
import com.cencen.bloommatecapstone.data.call.AddCartRequest
import com.cencen.bloommatecapstone.data.model.CartItem
import com.cencen.bloommatecapstone.data.preference.PreferenceSetting
import com.cencen.bloommatecapstone.util.toCartItem
import kotlinx.coroutines.flow.first
import java.net.SocketException

class CartRepository(
    private val api: ApiServices,
    private val preferenceSetting: PreferenceSetting
) {

    fun addCart(addCartRequest: AddCartRequest): LiveData<Libraries<Unit>> = liveData {
        emit(Libraries.Loading)
        try {
            val token = preferenceSetting.getUserData().first().credential
            val bearerToken = "Bearer $token"
            val response = api.addToCart(addCartRequest, bearerToken)
            if (response.isSuccessful) {
                emit(
                    Libraries.Success(Unit)
                )
            } else {
                emit(Libraries.Error(response.message()))
            }
        } catch (exception: SocketException) {
            emit(Libraries.Error("Cek your Internet"))
        } catch (exception: Exception) {
            emit(Libraries.Error("Something went Wrong"))
        }
    }

    suspend fun getCart(): LiveData<Libraries<List<CartItem>>> {
        val liveData = MutableLiveData<Libraries<List<CartItem>>>()
        liveData.postValue(Libraries.Loading)
        try {
            val token = preferenceSetting.getUserData().first().credential
            val bearerToken = "Bearer $token"
            val response = api.getCart(bearerToken)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    val list = body.data?.carts?.products.toCartItem()
                    Log.d("CartRepository", "data: $list")
                    liveData.postValue(Libraries.Success(list))
                }else{
                    liveData.postValue(Libraries.Success(emptyList()))
                }
            } else {
                liveData.postValue(Libraries.Error(response.message()))
            }
        } catch (exception: SocketException) {
            liveData.postValue(Libraries.Error("Cek your Internet"))
        } catch (exception: Exception) {
            liveData.postValue(Libraries.Error("Something went Wrong"))
        }
        return liveData
    }

    fun deleteProduct(productId: String): LiveData<Libraries<Unit>> = liveData {
        emit(Libraries.Loading)
        try {
            val token = preferenceSetting.getUserData().first().credential
            val bearerToken = "Bearer $token"
            val response = api.deleteProduct(bearerToken, productId)
            if (response.isSuccessful) {
                emit(
                    Libraries.Success(Unit)
                )
            } else {
                emit(Libraries.Error(response.message()))
            }
        } catch (exception: SocketException) {
            emit(Libraries.Error("Cek your Internet"))
        } catch (exception: Exception) {
            emit(Libraries.Error("Something went Wrong"))
        }
    }

    companion object {
        @Volatile
        private var instance: CartRepository? = null
        fun getInstance(
            con: PreferenceSetting,
            api: ApiServices
        ): CartRepository =
            instance ?: synchronized(this) {
                instance ?: CartRepository(api, con)

            }.also { instance = it }
    }
}