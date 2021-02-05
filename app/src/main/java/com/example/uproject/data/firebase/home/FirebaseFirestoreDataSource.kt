package com.example.uproject.data.firebase.home

import com.example.uproject.domain.model.Category
import com.example.uproject.domain.model.Product

interface FirebaseFirestoreDataSource {

    //Home
    //category
    suspend fun getListCategory(): List<Category>
    //Product
    suspend fun getListProducts(): List<Product>


}