package com.handearslan.capstoneproject.data.source.remote

import com.handearslan.capstoneproject.common.Constants.Endpoints.ADD_TO_CART
import com.handearslan.capstoneproject.common.Constants.Endpoints.CLEAR_CART
import com.handearslan.capstoneproject.common.Constants.Endpoints.DELETE_FROM_CART
import com.handearslan.capstoneproject.common.Constants.Endpoints.GET_CART_PRODUCTS
import com.handearslan.capstoneproject.data.model.AddToCartResponse
import com.handearslan.capstoneproject.data.model.CartItem
import com.handearslan.capstoneproject.data.model.ClearCartResponse
import com.handearslan.capstoneproject.data.model.DeleteFromCartItem
import com.handearslan.capstoneproject.data.model.DeleteFromCartResponse
import com.handearslan.capstoneproject.data.model.GetCartProductsResponse
import com.handearslan.capstoneproject.data.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CartService {

    @POST(ADD_TO_CART)
    fun addToCart(@Body cartItem: CartItem): Call<AddToCartResponse>

    @GET(GET_CART_PRODUCTS)
    fun getCartProducts(@Query("userId") userId: String?): Call<GetCartProductsResponse>

    @POST(DELETE_FROM_CART)
    fun deleteFromCart(
        @Body deleteFromCartItem: DeleteFromCartItem
    ): Call<DeleteFromCartResponse>

    @POST(CLEAR_CART)
    fun clearCart(@Body user: User): Call<ClearCartResponse>
}