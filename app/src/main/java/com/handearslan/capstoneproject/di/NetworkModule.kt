package com.handearslan.capstoneproject.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import android.content.Context
import com.handearslan.capstoneproject.common.Constants.BASE_URL
import com.handearslan.capstoneproject.data.source.remote.ProductService
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideChuckerInterceptor(@ApplicationContext context: Context) =
        ChuckerInterceptor.Builder(context).build()

    @Singleton
    @Provides
    fun provideOkHttp(chucker: ChuckerInterceptor) = OkHttpClient.Builder().apply {
        addInterceptor(
            Interceptor { chain ->
                val builder = chain.request().newBuilder()
                builder.header("store", "PureGlow")
                return@Interceptor chain.proceed(builder.build())
            }
        )
        addInterceptor(chucker)
    }.build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient) = Retrofit.Builder().apply {
        addConverterFactory(GsonConverterFactory.create())
        baseUrl(BASE_URL)
        client(okHttpClient)
    }.build()

    @Singleton
    @Provides
    fun provideProductService(retrofit: Retrofit) = retrofit.create(ProductService::class.java)
}