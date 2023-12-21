package com.cencen.bloommatecapstone.data.model

import com.google.gson.annotations.SerializedName

data class Login(
    @SerializedName("credential")
    val credential: String?
)
