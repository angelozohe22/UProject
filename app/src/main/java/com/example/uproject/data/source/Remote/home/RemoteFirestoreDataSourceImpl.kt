package com.example.uproject.data.source.Remote.home

import com.example.uproject.common.FirebaseFirestore
import com.example.uproject.core.preferences
import com.example.uproject.data.source.Remote.dto.FavoriteProductDto
import com.example.uproject.data.source.Remote.response.*
import com.google.firebase.FirebaseError
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import java.lang.Exception

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

    override suspend fun getFavoriteProductList(): List<FavoriteProductResponse> {
        val favoriteProductList = mutableListOf<FavoriteProductResponse>()

        try {
            val documentSnapshot = dbReference.collection("Favorite")
                .document(preferences.deviceToken ?: "").get().await()

            if (documentSnapshot != null){
                val favoriteProductMap = documentSnapshot.get("favoriteProductList") as Map<String, FavoriteProductResponse>
                favoriteProductMap.forEach {
                    val hashMapValues = it.value as Map<String, Any>
                    val productId = hashMapValues["productId"] as Long
                    val name     = hashMapValues["name"] as String
                    val weight   = hashMapValues["weight"] as String
                    val mark     = hashMapValues["mark"] as String
                    val urlImage = hashMapValues["urlImage"] as String
                    favoriteProductList.add(FavoriteProductResponse(productId, name, mark, weight, urlImage))
                }
            }
        }catch (error: Throwable){
            println(">>> hubo un problema ::: ${error.localizedMessage}")
        }

        return favoriteProductList.toList()
    }

    override suspend fun setFavoriteProduct(favoriteProduct: FavoriteProductDto) {
        val updates =
            hashMapOf("favoriteProductList" to
                    hashMapOf("${favoriteProduct.productId}" to favoriteProduct))
        dbReference.collection("Favorite")
            .document(preferences.deviceToken ?: "")
            .set(updates, SetOptions.merge())
            .await()
    }

    override suspend fun removeFavoriteProduct(favoriteProductId: Int){
        val updates = hashMapOf<String, Any>(
            "favoriteProductList.${favoriteProductId}" to FieldValue.delete()
        )
        dbReference.collection("Favorite")
            .document(preferences.deviceToken ?: "")
            .update(updates)
            .await()
    }
}