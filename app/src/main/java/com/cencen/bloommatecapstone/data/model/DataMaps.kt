package com.cencen.bloommatecapstone.data.model

data class DataMaps(
    val cover: String,
    val localName: String,
    val price: Long,
    val flowerName: String,
    val description: String,
    val id: String,
    val stock: Long,
    val seller: Seller,
)