package com.example.uproject.domain.model

data class User (
    val id: String,
    val username: String,
    val phone: String,
    val email: String
){
    constructor(): this("","","","")
}