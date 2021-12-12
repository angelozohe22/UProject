package com.example.uproject.data.Remote.home

import com.example.uproject.domain.model.Category
import com.example.uproject.domain.model.Product

interface RemoteFirestoreDataSource {

    //Home
    //category
    suspend fun getListCategory(): List<Category>
    //Product
    suspend fun getListProducts(): List<Product>


}