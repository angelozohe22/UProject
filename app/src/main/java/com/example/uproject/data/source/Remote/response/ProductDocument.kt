package com.example.uproject.data.source.Remote.response

data class ProductDocument(
    val productList: List<ProductResponse>
){
    constructor(): this(emptyList())
}
