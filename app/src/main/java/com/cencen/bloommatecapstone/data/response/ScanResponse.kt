package com.cencen.bloommatecapstone.data.response

import com.cencen.bloommatecapstone.data.model.DataFlower
import com.google.gson.annotations.SerializedName

data class ScanResponse(
    @SerializedName("status") val status: String? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName("data") val data: DataFlower? = null
)
