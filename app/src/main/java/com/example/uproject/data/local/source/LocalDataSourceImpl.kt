package com.example.uproject.data.local.source

import com.example.uproject.data.local.db.DulcekatDataBase
import com.example.uproject.data.local.db.category.CategoryEntity

class LocalDataSourceImpl(private val dulcekatDataBase: DulcekatDataBase): LocalDataSource {

    override suspend fun insertCategory(category: CategoryEntity) {
        dulcekatDataBase.dulcekatDao().insertCategory(category)
    }

    override suspend fun getListCategory(): List<CategoryEntity> {
        return dulcekatDataBase.dulcekatDao().getListCategory()
    }
}