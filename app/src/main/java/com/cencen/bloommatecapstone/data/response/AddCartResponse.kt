package com.cencen.bloommatecapstone.data.response

import com.google.gson.annotations.SerializedName

data class AddCartResponse (
    @SerializedName("status")
    val status: String?,

    @SerializedName("message")
    val message: String?,
)