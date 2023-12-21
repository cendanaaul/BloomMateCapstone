package com.cencen.bloommatecapstone.data.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("status")
    val status: String?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("credential")
    val credential: String?,
)
