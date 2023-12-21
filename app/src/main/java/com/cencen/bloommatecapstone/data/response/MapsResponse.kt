package com.cencen.bloommatecapstone.data.response

import com.cencen.bloommatecapstone.data.model.DataMaps

data class MapsResponse(
    val status: String,
    val message: String,
    val data: List<DataMaps>,
)