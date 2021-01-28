package com.example.uproject.data.local.source

import com.example.uproject.data.local.db.category.CategoryEntity

interface LocalDataSource {

    suspend fun insertCategory(category: CategoryEntity)
    suspend fun getListCategory(): List<CategoryEntity>

}