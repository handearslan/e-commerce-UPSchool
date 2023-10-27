package com.handearslan.capstoneproject.data.model.response

data class ProductUI(
    val id: Int,
    val title: String,
    val price: Double,
    val salePrice: Double,
    val description: String,
    val category: String,
    val imageOne: String,
    val rate: Double,
    val saleState: Boolean
)
