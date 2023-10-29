package com.handearslan.capstoneproject.data.mapper

import com.handearslan.capstoneproject.data.model.response.Product
import com.handearslan.capstoneproject.data.model.response.ProductEntity
import com.handearslan.capstoneproject.data.model.response.ProductListUI
import com.handearslan.capstoneproject.data.model.response.ProductUI


fun Product.mapToProductUI(favorites: List<Int>) =
    ProductUI(
        id = id ?: 1,
        title = title.orEmpty(),
        price = price ?: 0.0,
        salePrice = salePrice ?: 0.0,
        description = description.orEmpty(),
        category = category.orEmpty(),
        imageOne = imageOne.orEmpty(),
        rate = rate ?: 0.0,
        saleState = saleState ?: false,
        count = count ?: 0,
        isFav = favorites.contains(id)

    )

fun List<Product>.mapProductToProductUI(favorites: List<Int>) =
    map {
        ProductUI(
            id = it.id ?: 1,
            title = it.title.orEmpty(),
            price = it.price ?: 0.0,
            salePrice = it.salePrice ?: 0.0,
            saleState = it.saleState ?: false,
            imageOne = it.imageOne.orEmpty(),
            rate = it.rate ?: 0.0,
            count = it.count ?: 0,
            description = it.description.orEmpty(),
            category = it.category.orEmpty(),
            isFav = favorites.contains(it.id)

        )
    }

fun ProductUI.mapToProductEntity() =
    ProductEntity(
        productId = id,
        title = title,
        price = price,
        salePrice = salePrice,
        imageOne = imageOne,
        description = description,
        category = category,
        rate = rate,
        count = count,
        saleState = saleState,
    )

fun List<ProductEntity>.mapProductEntityToProductUI() =
    map {
        ProductUI(
            id = it.productId ?: 1,
            title = it.title.orEmpty(),
            price = it.price ?: 0.0,
            salePrice = it.salePrice ?: 0.0,
            imageOne = it.imageOne.orEmpty(),
            rate = it.rate ?: 0.0,
            count = it.count ?: 0,
            saleState = it.saleState ?: false,
            description = it.description.orEmpty(),
            category = it.category.orEmpty()
        )
    }