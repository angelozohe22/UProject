package com.example.uproject.data.firebase.home

import com.example.uproject.common.FirebaseFirestore
import com.example.uproject.domain.model.Category
import com.example.uproject.domain.model.CategoryDocument
import kotlinx.coroutines.tasks.await

class FirebaseFirestoreDataSourceImpl: FirebaseFirestoreDataSource {

    private val dbReference =  FirebaseFirestore.getInstance()

    override suspend fun getListCategory(): List<Category> {
        var listCategory = emptyList<Category>()
        val documentSnapshot = dbReference.collection("Category")
            .document("categories").get().await()

        if(documentSnapshot != null)
            listCategory = documentSnapshot.toObject(CategoryDocument::class.java)?.categoryList ?: emptyList()
        return listCategory
    }
}