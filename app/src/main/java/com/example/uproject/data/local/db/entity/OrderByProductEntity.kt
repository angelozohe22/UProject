package com.example.uproject.data.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "tb_order_by_product", foreignKeys = [ForeignKey(
    entity          = ProductEntity::class,
    parentColumns   = ["idproduct"],
    childColumns    = ["idproduct"],
    onDelete        = ForeignKey.CASCADE
), ForeignKey(
    entity          = OrderEntity::class,
    parentColumns   = ["id_order"],
    childColumns    = ["id_order"],
    onDelete        = ForeignKey.CASCADE
)])
data class OrderByProductEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_order_by_product")     val idOrderByProduct    : Int,
    @ColumnInfo(name = "idproduct")               val idproduct           : Int,
    @ColumnInfo(name = "id_order")                val idorder             : Int,
    @ColumnInfo(name = "quantity")                val quantity            : Int,
    @ColumnInfo(name = "price_by_one")            val priceByOne          : Double
)