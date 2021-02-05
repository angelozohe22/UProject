package com.example.uproject.data.local.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlin.math.roundToInt

@Parcelize
@Entity(tableName = "tb_product", foreignKeys = [ForeignKey(
    entity          = CategoryEntity::class,
    parentColumns   = ["idcategory"],
    childColumns    = ["_idcategory"],
    onDelete        = CASCADE
)])
data class ProductEntity(
    @ColumnInfo(name = "idproduct")
    @PrimaryKey                         val idproduct   : Int,
    @ColumnInfo(name = "name")          val name        : String,
    @ColumnInfo(name = "image")         val image       : String,
    @ColumnInfo(name = "mark")          val mark        : String,
    @ColumnInfo(name = "weight")        val weight      : String,
    @ColumnInfo(name = "price")         val price       : Double,
    @ColumnInfo(name = "quantity")      val quantity    : Int,
    @ColumnInfo(name = "description")   val description : String,
    @ColumnInfo(name = "offer")         val offer       : Int,
    @ColumnInfo(name = "isfavorite")    var isfavorite  : Int,
    @ColumnInfo(name = "_idcategory")   val _idcategory  : Int
): Parcelable
