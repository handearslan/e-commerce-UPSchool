package com.handearslan.capstoneproject.di



import com.google.firebase.auth.FirebaseAuth
import com.handearslan.capstoneproject.data.repository.AuthRepository
import com.handearslan.capstoneproject.data.repository.ProductRepository
import com.handearslan.capstoneproject.data.source.remote.ProductService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideProductRepository(productService: ProductService) = ProductRepository(productService)

    @Singleton
    @Provides
    fun provideAuthRepository() : AuthRepository = AuthRepository(firebaseAuth = FirebaseAuth.getInstance())



}