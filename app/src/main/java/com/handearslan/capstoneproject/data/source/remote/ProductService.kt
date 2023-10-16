package com.handearslan.capstoneproject.data.source.remote

import com.handearslan.capstoneproject.data.model.GetProductDetailResponse
import com.handearslan.capstoneproject.data.model.GetProductsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductService {

    @GET("get_products.php")
    fun getProducts(): Call<GetProductsResponse>

    @GET("get_product_detail.php")
    fun getProductDetail(
        @Query("id") id: Int
    ): Call<GetProductDetailResponse>
}