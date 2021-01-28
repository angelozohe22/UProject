package com.example.uproject.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.uproject.data.local.db.category.CategoryEntity

@Dao
interface DulcekatDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CategoryEntity)

    @Query("SELECT * FROM tb_category")
    suspend fun getListCategory(): List<CategoryEntity>
}