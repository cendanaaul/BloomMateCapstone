package com.cencen.bloommatecapstone.data.response

import com.google.gson.annotations.SerializedName

data class CartResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Seller(

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("sellerId")
	val sellerId: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("location_coordinate")
	val locationCoordinate: LocationCoordinate? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("ownerId")
	val ownerId: String? = null
)

data class ProductsItem(

	@field:SerializedName("cover")
	val cover: String? = null,

	@field:SerializedName("seller")
	val seller: Seller? = null,

	@field:SerializedName("quantity")
	val quantity: Int? = null,

	@field:SerializedName("productId")
	val productId: String? = null,

	@field:SerializedName("price")
	val price: Int? = null,

	@field:SerializedName("subtotal")
	val subtotal: Int? = null,

	@field:SerializedName("flowerName")
	val flowerName: String? = null
)

data class LocationCoordinate(

	@field:SerializedName("latitude")
	val latitude: Any? = null,

	@field:SerializedName("longitude")
	val longitude: Any? = null
)

data class Data(

	@field:SerializedName("carts")
	val carts: Carts? = null
)

data class Carts(

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("userId")
	val userId: String? = null,

	@field:SerializedName("products")
	val products: List<ProductsItem>? = null
)
