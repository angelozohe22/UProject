package com.example.uproject.data.firebase.home

import com.example.uproject.domain.model.Category

interface FirebaseFirestoreDataSource {

    //Home
    //category
    suspend fun getListCategory(): List<Category>

}