package com.example.uproject.data.Remote.home

import com.example.uproject.common.FirebaseFirestore
import com.example.uproject.core.aplication.Constants
import com.example.uproject.core.aplication.Constants.storeInstance
import com.example.uproject.domain.model.Category
import com.example.uproject.domain.model.CategoryDocument
import com.example.uproject.domain.model.Product
import com.example.uproject.domain.model.ProductDocument
import kotlinx.coroutines.tasks.await

class RemoteFirestoreDataSourceImpl: RemoteFirestoreDataSource {

    private val dbReference by lazy { storeInstance }

    override suspend fun getListCategory(): List<Category> {
        var listCategory = emptyList<Category>()
        val documentSnapshot = dbReference.collection("Category")
            .document("categories").get().await()

        if(documentSnapshot != null)
            listCategory = documentSnapshot.toObject(CategoryDocument::class.java)?.categoryList ?: emptyList()
        return listCategory
    }

    override suspend fun getListProducts(): List<Product> {
        var listProduct = emptyList<Product>()
        val documentSnapshot = dbReference.collection("Product")
            .document("products").get().await()

        if (documentSnapshot != null)
            listProduct = documentSnapshot.toObject(ProductDocument::class.java)?.productList ?: emptyList()
        return listProduct
    }
}