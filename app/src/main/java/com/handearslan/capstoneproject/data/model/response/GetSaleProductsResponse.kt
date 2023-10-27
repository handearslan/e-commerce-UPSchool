package com.handearslan.capstoneproject.data.model.response

data class GetSaleProductsResponse(
    val products: List<Product>?,

    ) : BaseResponse()