package com.example.uproject.data.local.db.entity

import androidx.room.ColumnInfo

data class BagProductEntity(
    @ColumnInfo(name = "idproduct") val idproduct: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "mark") val mark: String,
    @ColumnInfo(name = "weight") val weight: String,
    @ColumnInfo(name = "quantity") val quantity: Int,
    @ColumnInfo(name = "price_by_one") val price_by_one: Double
)
