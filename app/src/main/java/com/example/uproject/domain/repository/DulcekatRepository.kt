package com.example.uproject.domain.repository

import com.example.uproject.data.local.db.category.CategoryEntity
import com.example.uproject.domain.model.Category

interface DulcekatRepository{
    //Local
    suspend fun insertCategory(category: CategoryEntity)
    suspend fun getListCategory(): List<CategoryEntity>
    //Firebase
    suspend fun getListCategoryFirebase(): List<Category>
}