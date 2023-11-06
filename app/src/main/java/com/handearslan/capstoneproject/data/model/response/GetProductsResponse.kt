package com.handearslan.capstoneproject.data.model.response

data class GetProductsResponse(
    val products: List<Product>?,

    ) : BaseResponse()