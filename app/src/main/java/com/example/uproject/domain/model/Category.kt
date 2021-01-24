package com.example.uproject.domain.model

data class Category(
    val name  : String,
    val image : String
){
    constructor(): this("","")
}