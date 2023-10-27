package com.handearslan.capstoneproject.data.mapper

import com.handearslan.capstoneproject.data.model.response.Product
import com.handearslan.capstoneproject.data.model.response.ProductListUI
import com.handearslan.capstoneproject.data.model.response.ProductUI


fun Product.mapToProductUI() =
    ProductUI(
        id = id ?: 1,
        title = title.orEmpty(),
        price = price ?: 0.0,
        salePrice = salePrice ?: 0.0,
        description = description.orEmpty(),
        category = category.orEmpty(),
        imageOne = imageOne.orEmpty(),
        rate = rate ?: 0.0,
        saleState = saleState ?: false

    )

fun List<Product>.mapToProductListUI() =
    map {
        ProductListUI(
            id = it.id ?: 1,
            title = it.title.orEmpty(),
            price = it.price ?: 0.0,
            salePrice = it.salePrice ?: 0.0,
            saleState = it.saleState ?: false,
            imageOne = it.imageOne.orEmpty(),
            category = it.category.orEmpty()

        )
    }

