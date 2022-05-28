package com.example.uproject.data.source.Remote.home

import com.example.uproject.common.FirebaseFirestore
import com.example.uproject.data.source.Remote.response.CategoryDocument
import com.example.uproject.data.source.Remote.response.CategoryResponse
import com.example.uproject.data.source.Remote.response.ProductResponse
import com.example.uproject.data.source.Remote.response.ProductDocument
import kotlinx.coroutines.tasks.await

class RemoteFirestoreDataSourceImpl: RemoteFirestoreDataSource {

    private val dbReference by lazy { FirebaseFirestore.getInstance() }

    override suspend fun getListCategory(): List<CategoryResponse> {
        var listCategory = emptyList<CategoryResponse>()
        val documentSnapshot = dbReference.collection("Category")
            .document("categories").get().await()

        if(documentSnapshot != null)
            listCategory = documentSnapshot.toObject(CategoryDocument::class.java)?.categoryList ?: emptyList()
        return listCategory
    }

    override suspend fun getListProducts(): List<ProductResponse> {
        var listProduct = emptyList<ProductResponse>()
        val documentSnapshot = dbReference.collection("Product")
            .document("products").get().await()

        if (documentSnapshot != null)
            listProduct = documentSnapshot.toObject(ProductDocument::class.java)?.productList ?: emptyList()
        return listProduct
    }
}