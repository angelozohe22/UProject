package com.example.uproject.data.source.Remote.response

data class CategoryDocument(
    val categoryList: List<CategoryResponse>
){
    constructor(): this(emptyList())
}