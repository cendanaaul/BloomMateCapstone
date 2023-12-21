package com.cencen.bloommatecapstone.data.response

import com.cencen.bloommatecapstone.data.model.CatalogItem
import com.cencen.bloommatecapstone.data.model.FlowerItem
import com.google.gson.annotations.SerializedName

data class FlowerResponse(

    @SerializedName("status")
    val status: String?,

    @SerializedName("message")
    val message: String?,

    @SerializedName("data")
    val data: List<FlowerItem>
)

