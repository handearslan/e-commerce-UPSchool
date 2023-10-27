package com.handearslan.capstoneproject.data.source.remote

import com.handearslan.capstoneproject.common.Constants.Endpoints.ADD_TO_CART
import com.handearslan.capstoneproject.common.Constants.Endpoints.CLEAR_CART
import com.handearslan.capstoneproject.common.Constants.Endpoints.DELETE_FROM_CART
import com.handearslan.capstoneproject.common.Constants.Endpoints.GET_CART_PRODUCTS
import com.handearslan.capstoneproject.common.Constants.Endpoints.GET_PRODUCTS
import com.handearslan.capstoneproject.common.Constants.Endpoints.GET_PRODUCT_DETAIL
import com.handearslan.capstoneproject.common.Constants.Endpoints.GET_SALE_PRODUCTS
import com.handearslan.capstoneproject.common.Constants.Endpoints.SEARCH_PRODUCT
import com.handearslan.capstoneproject.data.model.request.AddToCartRequest
import com.handearslan.capstoneproject.data.model.request.ClearCartRequest
import com.handearslan.capstoneproject.data.model.response.BaseResponse
import com.handearslan.capstoneproject.data.model.request.DeleteFromCartRequest
import com.handearslan.capstoneproject.data.model.response.GetCartProductsResponse
import com.handearslan.capstoneproject.data.model.response.GetProductDetailResponse
import com.handearslan.capstoneproject.data.model.response.GetProductsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ProductService {

    @GET(GET_PRODUCTS)
    suspend fun getProducts(): Response<GetProductsResponse>

  /*  @GET(GET_SALE_PRODUCTS)
    suspend fun getSaleProducts(): Response<GetProductsResponse>*/

    @GET(GET_PRODUCT_DETAIL)
    suspend fun getProductDetail(
        @Query("id") id: Int
    ): Response<GetProductDetailResponse>

    @GET(SEARCH_PRODUCT)
    suspend fun getSearchResult(
        @Query("query") query: String
    ): Response<GetProductsResponse>

    @POST(ADD_TO_CART)
    suspend fun addToCart(@Body addToCartRequest: AddToCartRequest): Response<BaseResponse>

    @GET(GET_CART_PRODUCTS)
    suspend fun getCartProducts(@Query("userId") userId: String): Response<GetCartProductsResponse>

    @POST(DELETE_FROM_CART)
    suspend fun deleteFromCart(
        @Body deleteFromCartRequest: DeleteFromCartRequest
    ): Response<BaseResponse>

    @POST(CLEAR_CART)
    suspend fun clearCart(@Body clearCartRequest: ClearCartRequest): Response<BaseResponse>

}