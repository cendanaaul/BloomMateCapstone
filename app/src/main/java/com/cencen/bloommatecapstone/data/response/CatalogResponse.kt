package com.cencen.bloommatecapstone.data.response

import com.cencen.bloommatecapstone.data.model.CatalogItem
import com.cencen.bloommatecapstone.data.model.ListCatalog
import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody

data class CatalogResponse(

    @SerializedName("status")
    val status: String?,

    @SerializedName("message")
    val message: String?,

    @SerializedName("data")
    val data: List<CatalogItem>
)


