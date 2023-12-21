package com.cencen.bloommatecapstone.data.model

import com.google.gson.annotations.SerializedName

data class ProductsData(
    @SerializedName("cover"       ) var cover       : String? = null,
    @SerializedName("localName"   ) var localName   : String? = null,
    @SerializedName("description" ) var description : String? = null,
    @SerializedName("id"          ) var id          : String? = null,
    @SerializedName("price"       ) var price       : Int?    = null,
    @SerializedName("stock"       ) var stock       : Int?    = null,
    @SerializedName("flowerName"  ) var flowerName  : String? = null,
    @SerializedName("seller"      ) var seller      : Seller? = Seller()
)
