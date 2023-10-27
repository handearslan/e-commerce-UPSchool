package com.handearslan.capstoneproject.data.model.response

data class GetCartProductsResponse(
    val products: List<Product>?,

    ) : BaseResponse()
