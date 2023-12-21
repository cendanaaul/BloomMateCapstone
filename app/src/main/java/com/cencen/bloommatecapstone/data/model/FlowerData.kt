package com.cencen.bloommatecapstone.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FlowerData(
    @SerializedName("treatment"   ) val treatment   : String? = null,
    @SerializedName("description" ) val description : String? = null,
    @SerializedName("localName"   ) val localName   : String? = null,
    @SerializedName("id"          ) val id          : String? = null,
    @SerializedName("flowerName"  ) val flowerName  : String? = null,
    @SerializedName("klasifikasi" ) val klasifikasi : String? = null

): Parcelable

/*@SerializedName("cover"       ) val cover       : String? = null,
    @SerializedName("price"       ) val price       : Long? = null,*/
