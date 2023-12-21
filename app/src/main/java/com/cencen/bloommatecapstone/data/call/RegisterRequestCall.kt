package com.cencen.bloommatecapstone.data.call

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody

data class RegisterRequestCall(

    @field: SerializedName("fullname")
    val fullname: String? = null,

    @SerializedName("username")
    val username: String? = null,

    @SerializedName("email")
    val email: String? = null,

    @SerializedName("password")
    val password: String? = null,

    @SerializedName("address")
    val address: String? = null,

    @SerializedName("location_coordinate")
    val location_coordinate: LocationCoordinate? = null,

    @SerializedName("id")
    val id: String? = null,

    @SerializedName("contact")
    val contact: String? = null,

    @SerializedName("cover")
    val cover: MultipartBody.Part? = null,

    )

data class LocationCoordinate(

    @field:SerializedName("latitude")
    val latitude: Double? = null,

    @field:SerializedName("longitude")
    val longitude: Double? = null
)

/*
fullname(string,required)
username (string, required)
email (required),
password (string,required)
address (string,required)
locationLatitude (number,optional)
locationLongitude (number,optional)
contact (string, required)
cover: (file, optional)*/
