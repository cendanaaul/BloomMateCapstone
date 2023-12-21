package com.cencen.bloommatecapstone.data.call

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import retrofit2.http.Part

data class PredictRequest (
    /*@SerializedName("image")
    val image: MultipartBody.Part? = null,*/

    @Part val image: MultipartBody.Part
)