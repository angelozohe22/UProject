package com.example.uproject.domain.model

import com.example.uproject.data.source.Remote.dto.FavoriteProductDto

data class FavoriteProduct(
    val productId: Int = 0,
    val name     : String = "",
    val mark     : String = "",
    val weight   : String = "",
    val urlImage : String = ""
)

fun FavoriteProduct.toFavoriteProductDto() =
    FavoriteProductDto(
        productId = this.productId,
        name = this.name,
        mark = this.mark,
        weight = this.weight,
        urlImage = this.urlImage
    )