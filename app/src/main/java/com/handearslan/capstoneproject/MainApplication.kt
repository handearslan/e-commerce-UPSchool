package com.handearslan.capstoneproject

import android.app.Application
import android.content.Context
import com.handearslan.capstoneproject.data.source.remote.ProductService
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.handearslan.capstoneproject.data.source.remote.CartService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainApplication : Application() {

    companion object {

        private const val BASE_URL = "https://api.canerture.com/ecommerce/"

        var productService: ProductService? = null

        var cartService: CartService? = null

        fun provideRetrofit(context: Context) {

            val chucker = ChuckerInterceptor.Builder(context).build()

            val okHttpClient = OkHttpClient.Builder().apply {
                addInterceptor(
                    Interceptor { chain ->
                        val builder = chain.request().newBuilder()
                        builder.header("store", "PureGlow")
                        return@Interceptor chain.proceed(builder.build())
                    }
                )
                addInterceptor(chucker)
            }.build()

            val retrofit = Retrofit.Builder().apply {
                addConverterFactory(GsonConverterFactory.create())
                baseUrl(BASE_URL)
                client(okHttpClient)
            }.build()

            productService = retrofit.create(ProductService::class.java)

            cartService = retrofit.create(CartService::class.java)
        }
    }
}