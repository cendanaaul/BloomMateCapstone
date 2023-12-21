package com.cencen.bloommatecapstone.data.call

import com.google.gson.annotations.SerializedName

data class AddCartRequest(
    @SerializedName("productId") val productId: String,
    @SerializedName("quantity") val quantity: Int,

)
