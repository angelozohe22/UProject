package com.example.uproject.domain.model

data class ProductDocument(
    val productList: List<Product>
){
    constructor(): this(emptyList())
}
