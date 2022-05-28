package com.example.uproject.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_category")
data class CategoryEntity(
    @ColumnInfo(name = "idcategory")
    @PrimaryKey val idCategory: Int = 0,
    @ColumnInfo(name = "name")  val name: String,
    @ColumnInfo(name = "image") val image: String,
)