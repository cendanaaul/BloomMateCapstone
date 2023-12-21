package com.cencen.bloommatecapstone.util

import com.cencen.bloommatecapstone.data.model.CartItem
import com.cencen.bloommatecapstone.data.response.ProductsItem

fun List<ProductsItem>?.toCartItem(): List<CartItem> {
    return this?.map {
        CartItem(
            it.productId.orEmpty(), it.cover.orEmpty(),it.productId.orEmpty(), it.flowerName.orEmpty(),it.price?.toDouble() ?: 0.0,
            it.quantity?.toInt() ?: 0
        )
    } ?: emptyList()
}