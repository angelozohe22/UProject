package com.example.uproject.data.source.Remote.response

data class CategoryResponse(
    val name  : String,
    val image : String
){
    constructor(): this("","")
}