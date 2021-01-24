package com.example.uproject.domain.model

data class CategoryDocument(
    val categoryList: List<Category>
){
    constructor(): this(emptyList())
}