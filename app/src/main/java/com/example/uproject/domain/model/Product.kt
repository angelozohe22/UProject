package com.example.uproject.domain.model

data class Product(
    val name        : String,
    val image       : String,
    val mark        : String,
    val weight      : String,
    val price       : Double,
    val quantity    : Int,
    val description : String,
    val offer       : Int,
    val isfavorite  : Int,
    val _idcategory : Int
){
    constructor():this("","","","",0.0,0,"",0,0,0)
}