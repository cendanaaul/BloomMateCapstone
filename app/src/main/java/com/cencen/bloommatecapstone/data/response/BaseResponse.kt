package com.cencen.bloommatecapstone.data.response

import com.google.gson.annotations.SerializedName

data class BaseResponse(

    @field:SerializedName("data")
    val data: Data?= null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: String? = null,
)
/*data class Data(

    @field:SerializedName("user")
    val user: User? = null,
)*/
data class User(

    @field:SerializedName("id")
    val id: String? = null,
)
