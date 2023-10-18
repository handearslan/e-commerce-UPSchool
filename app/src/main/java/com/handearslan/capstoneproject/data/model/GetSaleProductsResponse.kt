package com.handearslan.capstoneproject.data.model

data class GetSaleProductsResponse(
    val products: List<Product>?,
    val status: Int?,
    val message: String?
)