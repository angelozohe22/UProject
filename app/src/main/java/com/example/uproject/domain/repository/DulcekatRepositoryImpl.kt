package com.example.uproject.domain.repository

import com.example.uproject.data.firebase.home.FirebaseFirestoreDataSource
import com.example.uproject.data.local.db.category.CategoryEntity
import com.example.uproject.data.local.source.LocalDataSource
import com.example.uproject.domain.model.Category

class DulcekatRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val firebaseFirestoreDataSource: FirebaseFirestoreDataSource
): DulcekatRepository {

    override suspend fun insertCategory(category: CategoryEntity) {
        localDataSource.insertCategory(category)
    }

    override suspend fun getListCategory(): List<CategoryEntity> {
        return localDataSource.getListCategory()
    }

    override suspend fun getListCategoryFirebase(): List<Category> {
        return firebaseFirestoreDataSource.getListCategory()
    }
}