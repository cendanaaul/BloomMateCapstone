package com.cencen.bloommatecapstone.data.model

import com.cencen.bloommatecapstone.data.call.LocationCoordinate
import com.google.gson.annotations.SerializedName

data class Seller(
    @SerializedName("cover"               ) var cover              : String?             = null,
    @SerializedName("address"             ) var address            : String?             = null,
    @SerializedName("sellerId"            ) var sellerId           : String?             = null,
    @SerializedName("name"                ) var name               : String?             = null,
    @SerializedName("location_coordinate" ) var locationCoordinate : LocationCoordinate? = LocationCoordinate(),
    @SerializedName("description"         ) var description        : String?             = null,
    @SerializedName("ownerId"             ) var ownerId            : String?             = null
)
