package com.cencen.bloommatecapstone.data.model

import com.google.gson.annotations.SerializedName


data class DataFlower(
    @SerializedName("predictResult" ) var predictResult : String?                 = null,
    @SerializedName("flowerData"    ) var flowerData    : FlowerItem?             = FlowerItem(),
    @SerializedName("productsData"  ) var productsData  : ArrayList<ProductsData> = arrayListOf()
)
