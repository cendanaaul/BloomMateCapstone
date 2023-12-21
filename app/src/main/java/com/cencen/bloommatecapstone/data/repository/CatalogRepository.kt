package com.cencen.bloommatecapstone.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.cencen.bloommatecapstone.data.CatalogPageSource
import com.cencen.bloommatecapstone.data.FlowerPageSource
import com.cencen.bloommatecapstone.data.Libraries
import com.cencen.bloommatecapstone.data.api.ApiServices
import com.cencen.bloommatecapstone.data.call.LoginRequestCall
import com.cencen.bloommatecapstone.data.call.PredictRequest
import com.cencen.bloommatecapstone.data.model.CatalogItem
import com.cencen.bloommatecapstone.data.model.FlowerItem
import com.cencen.bloommatecapstone.data.model.User
import com.cencen.bloommatecapstone.data.preference.PreferenceSetting
import com.cencen.bloommatecapstone.data.response.BaseResponse
import com.cencen.bloommatecapstone.data.response.CatalogResponse
import com.cencen.bloommatecapstone.data.response.FlowerResponse
import com.cencen.bloommatecapstone.data.response.LoginResponse
import com.cencen.bloommatecapstone.data.response.MapsResponse
import com.cencen.bloommatecapstone.data.response.PredictResponse
import com.cencen.bloommatecapstone.data.response.ScanResponse
import okhttp3.MultipartBody

class CatalogRepository (private val con: PreferenceSetting, private val api: ApiServices) {

    suspend fun login() {
        con.login()
    }

    fun userLogin(email: String, password: String): LiveData<Libraries<LoginResponse>> = liveData {
        emit(Libraries.Loading)
        try {
            val response = api.login(LoginRequestCall(email, password))
            emit(Libraries.Success(response))
        } catch (e: Exception) {
            Log.d("Login ", e.message.toString())
            emit(Libraries.Error(e.message.toString()))
        }
    }

    fun userRegister(
        fullname: String,
        username: String,
        email: String,
        password: String,
        address: String,
        contact: String,
    ) : LiveData<Libraries<BaseResponse>> =
        liveData {
            try {
                emit(Libraries.Loading)
                val requestBody = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("fullname", fullname)
                    .addFormDataPart("username", username)
                    .addFormDataPart("email", email)
                    .addFormDataPart("password", password)
                    .addFormDataPart("contact", contact)
                    .addFormDataPart("address", address)
                    .addFormDataPart("locationLatitude", "10" )
                    .addFormDataPart("locationLongitude", "-10")
                    .build()
                val res = api.register(requestBody)
                emit(Libraries.Success(res))
            } catch (e: Exception) {
                Log.d("Register ", e.message.toString())
                emit(Libraries.Error(e.message.toString()))
            }
        }

    suspend fun saveUserData(user: User) {
        con.saveUserData(user)
    }

    fun getCatalog(): LiveData<PagingData<CatalogItem>> {
        return Pager(
            config = PagingConfig(pageSize = 5),
            pagingSourceFactory = {
                CatalogPageSource(api)
            }
        ).liveData
    }

    fun getFlower(): LiveData<PagingData<FlowerItem>> {
        return Pager(
            config = PagingConfig(pageSize = 5),
            pagingSourceFactory = {
                FlowerPageSource(api)
            }
        ).liveData
    }

    fun getFlowerSeller(flowerName: String): LiveData<Libraries<MapsResponse>> =
        liveData {
            emit(Libraries.Loading)
            try {
                val respon = api.getFlowerSeller(flowerName)
                emit(Libraries.Success(respon))
            } catch (e: Exception) {
                Log.d("GetFlowerSeller ", e.message.toString())
                emit(Libraries.Error(e.message.toString()))
            }
        }

    suspend fun getFlowerDetailsByName(flowerName: String): FlowerResponse {
        return api.getFlowerDetailsByName(flowerName)
    }

    suspend fun predictFlower(image: MultipartBody.Part): Libraries<PredictResponse> {
        return try {
            val response = api.predict(image)
            Libraries.Success(response)
        } catch (e: Exception) {
            Log.d("Predict Flower", e.message.toString())
            Libraries.Error(e.message.toString())
        }
    }


    suspend fun getFlowerDetailsById(flowerId: String): FlowerResponse {
        return api.getFlowerDetailsById(flowerId)
    }

    suspend fun logout() {
        con.logout()
    }

    companion object {
        @Volatile
        private var instance: CatalogRepository? = null
        fun getInstance(
            con: PreferenceSetting,
            api: ApiServices
        ): CatalogRepository =
            instance ?: synchronized(this) {
                instance ?: CatalogRepository(con, api)
            }.also { instance = it }
    }
}