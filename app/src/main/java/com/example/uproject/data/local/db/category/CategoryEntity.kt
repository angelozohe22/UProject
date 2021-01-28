package com.example.uproject.data.local.db.category

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "tb_category")
data class CategoryEntity(
    @ColumnInfo(name = "idCategory")
    @PrimaryKey val idCategory: Int = 0,
    @ColumnInfo(name = "name")  val name: String,
    @ColumnInfo(name = "image") val image: String,
): Parcelable