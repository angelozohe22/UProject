package com.example.uproject.domain.model

import android.os.Parcelable
import com.example.uproject.data.source.Remote.response.ProductResponse
import com.example.uproject.data.source.local.entity.ProductEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val idproduct   : Int,
    val name        : String,
    val image       : String,
    val mark        : String,
    val weight      : String,
    val price       : Double,
    val quantity    : Int,
    val description : String,
    val offer       : Int,
    var isfavorite  : Int,
    val idcategory  : Int
): Parcelable

fun ProductResponse.toProductEntity(id: Int) =
    ProductEntity(
        idproduct   = id,
        name = this.name,
        image = this.image,
        mark = this.mark,
        weight = this.weight,
        price = this.price,
        quantity = this.quantity,
        description = this.description,
        offer = this.offer,
        isfavorite = this.isfavorite,
        _idcategory = this._idcategory
    )

fun ProductResponse.toProduct(id: Int) =
    Product(
        idproduct = id,
        name = this.name,
        image = this.image,
        mark = this.mark,
        weight = this.weight,
        price = this.price,
        quantity = this.quantity,
        description = this.description,
        offer = this.offer,
        isfavorite = this.isfavorite,
        idcategory = this._idcategory
    )

fun ProductEntity.toProduct() =
    Product(
        idproduct = this.idproduct,
        name = this.name,
        image = this.image,
        mark = this.mark,
        weight = this.weight,
        price = this.price,
        quantity = this.quantity,
        description = this.description,
        offer = this.offer,
        isfavorite = this.isfavorite,
        idcategory = this._idcategory
    )