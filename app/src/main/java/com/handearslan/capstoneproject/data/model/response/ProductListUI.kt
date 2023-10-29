package com.handearslan.capstoneproject.data.model.response

data class ProductListUI(
    val id: Int,
    val title: String,
    val price: Double,
    val saleState: Boolean,
    val salePrice: Double,
    val imageOne: String,
    val category: String,
    val isFav: Boolean = false

    )