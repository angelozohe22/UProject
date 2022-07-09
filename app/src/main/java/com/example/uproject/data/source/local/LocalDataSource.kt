package com.example.uproject.data.source.local

import com.example.uproject.data.source.local.entity.*

interface LocalDataSource {

    //Category
    suspend fun setCategoryList(categoryList: List<CategoryEntity>)
    suspend fun getListCategory(): List<CategoryEntity>

    //Product
    suspend fun setProductList(productList: List<ProductEntity>)
    suspend fun getListProductsByCategory(idcategory: Int): List<ProductEntity>
    suspend fun getListSomeProducts(): List<ProductEntity>
    suspend fun updateProductToFavorite(idProduct: Int, isFavorite: Int)
    suspend fun getListSimilarProducts(idProduct: Int, nameKey: String): List<ProductEntity>
    suspend fun searchProductsByName(nameKey: String): List<ProductEntity>
    suspend fun getFavoriteProductList(): List<ProductEntity>
    suspend fun getProductById(productId: Int): ProductEntity

    //Order
    suspend fun getListOrders(): List<OrderEntity>
    suspend fun insertNewOrder(order: OrderEntity)

    //OrderByProduct
    suspend fun insertOrderByProduct(orderByProduct: OrderByProductEntity)
    suspend fun getOrderByProductList(): List<BagProductEntity>
    suspend fun deleteProductFromBag(idProduct: Int)
    suspend fun deleteAllOrdersByProducts()

}