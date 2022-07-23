package com.example.uproject.domain.repository

import com.example.uproject.domain.model.Category
import com.example.uproject.domain.model.Product

interface  HomeRepository {

    suspend fun getListCategoryFirebase(): List<Category>
    suspend fun getListProductFirebase(): List<Product>

}