package com.handearslan.capstoneproject.data.source.remote

import com.handearslan.capstoneproject.common.Constants.Endpoints.GET_PRODUCTS
import com.handearslan.capstoneproject.common.Constants.Endpoints.GET_PRODUCT_DETAIL
import com.handearslan.capstoneproject.common.Constants.Endpoints.SEARCH_PRODUCT
import com.handearslan.capstoneproject.data.model.GetProductDetailResponse
import com.handearslan.capstoneproject.data.model.GetProductsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductService {

    @GET(GET_PRODUCTS)
    fun getProducts(): Call<GetProductsResponse>

    @GET(GET_PRODUCT_DETAIL)
    fun getProductDetail(
        @Query("id") id: Int
    ): Call<GetProductDetailResponse>

    @GET(SEARCH_PRODUCT)
    fun getSearchResult(
        @Query("query") query: String
    ): Call<GetProductsResponse>

}