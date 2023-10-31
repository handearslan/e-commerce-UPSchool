package com.handearslan.capstoneproject.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.handearslan.capstoneproject.data.model.response.ProductEntity

@Database(entities = [ProductEntity::class], version = 1)
abstract class ProductRoomDB : RoomDatabase() {

    abstract fun productDao(): ProductDao
}