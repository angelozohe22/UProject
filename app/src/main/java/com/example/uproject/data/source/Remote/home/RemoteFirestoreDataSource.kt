package com.example.uproject.data.source.Remote.home

import com.example.uproject.data.source.Remote.dto.FavoriteProductDto
import com.example.uproject.data.source.Remote.response.CategoryResponse
import com.example.uproject.data.source.Remote.response.FavoriteProductResponse
import com.example.uproject.data.source.Remote.response.ProductResponse

interface RemoteFirestoreDataSource {

    //Home
    //category
    suspend fun getListCategory(): List<CategoryResponse>
    //Product
    suspend fun getListProducts(): List<ProductResponse>

    //Favorite
    suspend fun getFavoriteProductList(): List<FavoriteProductResponse>
    suspend fun setFavoriteProduct(favoriteProduct: FavoriteProductDto)
    suspend fun removeFavoriteProduct(favoriteProductId: Int)

}