package com.handearslan.capstoneproject.data.model

data class GetCartProductsResponse(
    val products: List<Product>?,
    val status: Int?,
    val message: String?
)
