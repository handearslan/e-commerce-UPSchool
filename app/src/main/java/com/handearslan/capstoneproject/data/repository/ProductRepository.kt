package com.handearslan.capstoneproject.data.repository

import com.handearslan.capstoneproject.common.Resource
import com.handearslan.capstoneproject.data.mapper.mapToProductListUI
import com.handearslan.capstoneproject.data.mapper.mapToProductUI
import com.handearslan.capstoneproject.data.model.response.BaseResponse
import com.handearslan.capstoneproject.data.model.request.AddToCartRequest
import com.handearslan.capstoneproject.data.model.request.ClearCartRequest
import com.handearslan.capstoneproject.data.model.request.DeleteFromCartRequest
import com.handearslan.capstoneproject.data.model.response.ProductListUI
import com.handearslan.capstoneproject.data.model.response.ProductUI
import com.handearslan.capstoneproject.data.source.remote.ProductService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRepository(
    private val productService: ProductService
) {

    suspend fun getProducts(): Resource<List<ProductListUI>> =
        withContext(Dispatchers.IO) {
            try {
                val response = productService.getProducts().body()

                if (response?.status == 200) {
                    Resource.Success(response.products.orEmpty().mapToProductListUI())
                } else {
                    Resource.Fail(response?.message.orEmpty())
                }
            } catch (e: Exception) {
                Resource.Error(e.message.orEmpty())
            }
        }

    suspend fun getProductDetail(id: Int): Resource<ProductUI> =
        withContext(Dispatchers.IO) {
            try {
                val response = productService.getProductDetail(id).body()

                if (response?.status == 200 && response.product != null) {
                    Resource.Success(response.product.mapToProductUI())
                } else {
                    Resource.Fail(response?.message.orEmpty())
                }
            } catch (e: Exception) {
                Resource.Error(e.message.orEmpty())
            }
        }

    suspend fun addToCart(addToCartRequest: AddToCartRequest): Resource<BaseResponse> =
        withContext(Dispatchers.IO) {
            try {
                val response = productService.addToCart(addToCartRequest).body()
                if (response?.status == 200) {
                    Resource.Success(response)
                } else {
                    Resource.Fail(response?.message.orEmpty())
                }
            } catch (e: Exception) {
                Resource.Error(e.message.orEmpty())
            }
        }

    suspend fun getCartProducts(userId: String): Resource<List<ProductListUI>> =
        withContext(Dispatchers.IO) {
            try {
                val response = productService.getCartProducts(userId).body()

                if (response?.status == 200 && response.products != null) {
                    Resource.Success(response.products.orEmpty().mapToProductListUI())
                } else {
                    Resource.Fail(response?.message.orEmpty())
                }
            } catch (e: Exception) {
                Resource.Error(e.message.orEmpty())
            }
        }

    suspend fun clearCart(clearCartRequest: ClearCartRequest): Resource<BaseResponse> =
        withContext(Dispatchers.IO) {
            try {
                val response = productService.clearCart(clearCartRequest).body()
                if (response?.status == 200) {
                    Resource.Success(response)
                } else {
                    Resource.Fail(response?.message.orEmpty())
                }
            } catch (e: Exception) {
                Resource.Error(e.message.orEmpty())
            }
        }

    suspend fun onDeleteClick(id: Int): Resource<BaseResponse> =
        withContext(Dispatchers.IO) {
            try {
                val response = productService.deleteFromCart(DeleteFromCartRequest(id)).body()
                if (response?.status == 200) {
                    Resource.Success(response)
                } else {
                    Resource.Fail(response?.message.orEmpty())
                }
            } catch (e: Exception) {
                Resource.Error(e.message.orEmpty())
            }
        }

    suspend fun getSearchResult(query: String): Resource<List<ProductListUI>> =
        withContext(Dispatchers.IO) {
            try {
                val response = productService.getSearchResult(query).body()

                if (response?.status == 200) {
                    Resource.Success(response.products.orEmpty().mapToProductListUI())
                } else {
                    Resource.Fail(response?.message.orEmpty())
                }
            } catch (e: Exception) {
                Resource.Error(e.message.orEmpty())
            }
        }
}