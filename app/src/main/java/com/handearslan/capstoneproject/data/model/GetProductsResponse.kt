package com.handearslan.capstoneproject.data.model

data class GetProductsResponse(
    val products: List<Product>?,
    val status: Int?,
    val message: String?
)
