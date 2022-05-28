package com.example.uproject.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_order")
data class OrderEntity(
    @ColumnInfo(name = "id_order")
    @PrimaryKey(autoGenerate = true)    val idorder    : Int,
    @ColumnInfo(name = "id_user")       val iduser     : String,
    @ColumnInfo(name = "date_order")    val dateOrder  : String,
    @ColumnInfo(name = "flag_order")    val flagOrder  : Boolean
)
