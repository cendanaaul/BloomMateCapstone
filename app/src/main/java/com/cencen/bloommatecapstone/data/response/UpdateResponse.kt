package com.cencen.bloommatecapstone.data.response

import com.google.gson.annotations.SerializedName

data class UpdateResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)
