package com.cencen.bloommatecapstone.ui.dictionary

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.cencen.bloommatecapstone.data.Libraries
import com.cencen.bloommatecapstone.data.api.ApiConfig
import com.cencen.bloommatecapstone.data.model.FlowerItem
import com.cencen.bloommatecapstone.data.repository.CatalogRepository
import com.cencen.bloommatecapstone.data.response.FlowerResponse
import com.cencen.bloommatecapstone.data.response.PredictResponse
import com.cencen.bloommatecapstone.data.response.ScanResponse
import okhttp3.MultipartBody

class DictionaryViewModel(private val rep: CatalogRepository) : ViewModel() {

    val api = ApiConfig.getApiInstance()

    fun getFlower(): LiveData<PagingData<FlowerItem>> {
        return rep.getFlower().cachedIn(viewModelScope)
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

    suspend fun getFlowerDetailsByName(flowerName: String): Libraries<FlowerResponse> {
        return try {
            val response = rep.getFlowerDetailsByName(flowerName)
            Libraries.Success(response)
        } catch (e: Exception) {
            Log.d("getFlowerDetailsByName", "Exception: ${e.message}")
            Libraries.Error(e.message.toString())
        }
    }

    fun getFlowerDetailsByFlowerId(flowerId: String): LiveData<Libraries<FlowerItem?>> {
        return liveData {
            emit(Libraries.Loading)
            try {
                val response = rep.getFlowerDetailsById(flowerId)
                val flowerItem = convertFlowerResponseToFlowerItem(response)
                emit(Libraries.Success(flowerItem))
            } catch (e: Exception) {
                emit(Libraries.Error(e.message.toString()))
            }
        }
    }

    private fun convertFlowerResponseToFlowerItem(response: FlowerResponse): FlowerItem? {
        return response.data?.firstOrNull() // Take the first item, or return null if the list is empty
    }

}
