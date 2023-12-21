package com.cencen.bloommatecapstone.data.api

import com.cencen.bloommatecapstone.data.call.AddCartRequest
import com.cencen.bloommatecapstone.data.call.LoginRequestCall
import com.cencen.bloommatecapstone.data.call.PredictRequest
import com.cencen.bloommatecapstone.data.response.AddCartResponse
import com.cencen.bloommatecapstone.data.response.BaseResponse
import com.cencen.bloommatecapstone.data.response.CartResponse
import com.cencen.bloommatecapstone.data.response.CatalogResponse
import com.cencen.bloommatecapstone.data.response.DeleteResponse
import com.cencen.bloommatecapstone.data.response.FlowerResponse
import com.cencen.bloommatecapstone.data.response.LoginResponse
import com.cencen.bloommatecapstone.data.response.MapsResponse
import com.cencen.bloommatecapstone.data.response.PredictResponse
import com.cencen.bloommatecapstone.data.response.ScanResponse
import com.cencen.bloommatecapstone.data.response.UpdateResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {


    @POST("user")
    @Headers("Accept: application/json")
    suspend fun register(
        @Body body: RequestBody
    ): BaseResponse

    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequestCall
    ): LoginResponse

    @GET("flower")
    suspend fun getCatalog(
    ): CatalogResponse

    @GET("catalog/flower")
    suspend fun getFlower(
    ): FlowerResponse


    @Multipart
    @POST("flower/predict")
    @Headers("Accept: application/json")
    suspend fun predict(
        @Part image: MultipartBody.Part
    ) : PredictResponse

    @GET("catalog/flower")
    suspend fun getFlowerDetailsByName(
        @Query("flowerName") flowerName: String
    ): FlowerResponse

    @GET("search/flowers")
    suspend fun getFlowerSeller(
        @Query("flowerName") flowerName: String
    ): MapsResponse


    @GET("catalog/{flowerId}")
    suspend fun getFlowerDetailsById(
        @Path("flowerId") flowerId: String
    ): FlowerResponse

    @POST("cart/product")
    suspend fun addToCart(
        @Body request: AddCartRequest,
        @Header("Authorization") token: String
    ) : Response<AddCartResponse>

    @GET("cart")
    suspend fun getCart(
        @Header("Authorization") token: String
    ): Response<CartResponse>

    @DELETE("cart/product/{productId}")
    suspend fun deleteProduct(
        @Header("Authorization") token: String,
        @Path("productId") productId: String
    ): Response<DeleteResponse>

    @PUT("user")
    @Headers("Accept: application/json")
    suspend fun update(
        @Body body: RequestBody
    ): UpdateResponse


}