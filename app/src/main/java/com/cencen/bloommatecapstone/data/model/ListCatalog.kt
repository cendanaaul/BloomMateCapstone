package com.cencen.bloommatecapstone.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListCatalog(

    @SerializedName("flowerName")
    val flowerName: String? = null,

    @SerializedName("id")
    val id: String? = null,

    @SerializedName("localName")
    val localName: String? = null,

    @SerializedName("cover")
    val cover: String? = null,

    @SerializedName("sellerId")
    val sellerId: String? = null,

    @SerializedName("price")
    val price: Long? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("treatment")
    val treatment: String? = null,

    @SerializedName("stock")
    val stock: Int? = null

): Parcelable
