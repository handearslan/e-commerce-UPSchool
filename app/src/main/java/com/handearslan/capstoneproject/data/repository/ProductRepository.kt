package com.handearslan.capstoneproject.data.repository

import com.handearslan.capstoneproject.common.Resource
import com.handearslan.capstoneproject.data.mapper.mapProductEntityToProductUI
import com.handearslan.capstoneproject.data.mapper.mapProductToProductUI
import com.handearslan.capstoneproject.data.mapper.mapToProductEntity
import com.handearslan.capstoneproject.data.mapper.mapToProductUI
import com.handearslan.capstoneproject.data.model.request.AddToCartRequest
import com.handearslan.capstoneproject.data.model.request.ClearCartRequest
import com.handearslan.capstoneproject.data.model.request.DeleteFromCartRequest
import com.handearslan.capstoneproject.data.model.response.BaseResponse
import com.handearslan.capstoneproject.data.model.response.ProductUI
import com.handearslan.capstoneproject.data.source.local.ProductDao
import com.handearslan.capstoneproject.data.source.remote.ProductService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRepository(
    private val productService: ProductService,
    private val productDao: ProductDao
) {

    suspend fun getProducts(userId: String): Resource<List<ProductUI>> =
        withContext(Dispatchers.IO) {
            try {
                val favorites = productDao.getProductIds(userId)
                val response = productService.getProducts().body()

                if (response?.status == 200) {
                    Resource.Success(response.products.orEmpty().mapProductToProductUI(favorites))
                } else {
                    Resource.Fail(response?.message.orEmpty())
                }
            } catch (e: Exception) {
                Resource.Error(e.message.orEmpty())
            }
        }

    suspend fun getProductDetail(id: Int, userId: String): Resource<ProductUI> =
        withContext(Dispatchers.IO) {
            try {
                val favorites = productDao.getProductIds(userId)
                val response = productService.getProductDetail(id).body()

                if (response?.status == 200 && response.product != null) {
                    Resource.Success(response.product.mapToProductUI(favorites))
                } else {
                    Resource.Fail(response?.message.orEmpty())
                }
            } catch (e: Exception) {
                Resource.Error(e.message.orEmpty())
            }
        }

    suspend fun addToCart(id: Int, userId: String): Resource<BaseResponse> =
        withContext(Dispatchers.IO) {
            try {
                val response = productService.addToCart(AddToCartRequest(id, userId)).body()
                if (response?.status == 200) {
                    Resource.Success(response)
                } else {
                    Resource.Fail(response?.message.orEmpty())
                }
            } catch (e: Exception) {
                Resource.Error(e.message.orEmpty())
            }
        }

    suspend fun getCartProducts(userId: String): Resource<List<ProductUI>> =
        withContext(Dispatchers.IO) {
            try {
                val favorites = productDao.getProductIds(userId)
                val response = productService.getCartProducts(userId).body()

                if (response?.status == 200 && response.products != null) {
                    Resource.Success(response.products.mapProductToProductUI(favorites))
                } else {
                    Resource.Fail(response?.message.orEmpty())
                }
            } catch (e: Exception) {
                Resource.Error(e.message.orEmpty())
            }
        }

    suspend fun clearCart(userId: String): Resource<BaseResponse> =
        withContext(Dispatchers.IO) {
            try {
                val response = productService.clearCart(ClearCartRequest(userId)).body()
                if (response?.status == 200) {
                    Resource.Success(response)
                } else {
                    Resource.Fail(response?.message.orEmpty())
                }
            } catch (e: Exception) {
                Resource.Error(e.message.orEmpty())
            }
        }

    suspend fun onDeleteClick(id: Int, userId: String): Resource<BaseResponse> =
        withContext(Dispatchers.IO) {
            try {
                val response = productService.deleteFromCart(DeleteFromCartRequest(userId, id)).body()
                if (response?.status == 200) {
                    Resource.Success(response)
                } else {
                    Resource.Fail(response?.message.orEmpty())
                }
            } catch (e: Exception) {
                Resource.Error(e.message.orEmpty())
            }
        }

    suspend fun getSearchResult(query: String, userId: String): Resource<List<ProductUI>> =
        withContext(Dispatchers.IO) {
            try {
                val favorites = productDao.getProductIds(userId)
                val response = productService.getSearchResult(query).body()

                if (response?.status == 200) {
                    Resource.Success(response.products.orEmpty().mapProductToProductUI(favorites))
                } else {
                    Resource.Fail(response?.message.orEmpty())
                }
            } catch (e: Exception) {
                Resource.Error(e.message.orEmpty())
            }
        }

    suspend fun addToFavorites(productUI: ProductUI, userId: String) {
        productDao.addProduct(productUI.mapToProductEntity(userId))
    }

    suspend fun deleteFromFavorites(productUI: ProductUI, userId: String) {
        productDao.deleteProduct(productUI.mapToProductEntity(userId))
    }

    suspend fun clearAllFromFavorites(userId: String) {
        productDao.clearAllFromFavorites(userId)
    }

    suspend fun getFavorites(userId: String): Resource<List<ProductUI>> =
        withContext(Dispatchers.IO) {
            try {
                val products = productDao.getProducts(userId)

                if (products.isEmpty()) {
                    Resource.Fail("")
                } else {
                    Resource.Success(products.mapProductEntityToProductUI())
                }
            } catch (e: Exception) {
                Resource.Error(e.message.orEmpty())
            }
        }
}