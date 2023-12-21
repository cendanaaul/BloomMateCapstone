package com.cencen.bloommatecapstone.data.model

data class CartItem(
    val id: String,
    val cover:String,
    val productId: String,
    val name: String,
    val price: Double,
    val quantity: Int
)
