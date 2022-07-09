package com.example.uproject.data.source.Remote.response

import com.example.uproject.domain.model.FavoriteProduct

data class FavoriteProductResponse(
    val productId: Long,
    val name     : String,
    val mark     : String,
    val weight   : String,
    val urlImage : String
){
    constructor(): this(0,"","", "","")
}

data class FavoriteProductDocument(
    val favoriteProductList: List<FavoriteProductResponse>
){
    constructor(): this(emptyList())
}

fun FavoriteProductResponse.toFavoriteProduct() =
    FavoriteProduct(
        productId   = this.productId.toInt(),
        name        = this.name,
        mark        = this.mark,
        weight      = this.weight,
        urlImage    = this.urlImage,
    )

