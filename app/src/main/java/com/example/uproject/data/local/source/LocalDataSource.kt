package com.example.uproject.data.local.source

import com.example.uproject.data.local.db.entity.*

interface LocalDataSource {

    //Category
    suspend fun insertCategory(category: CategoryEntity)
    suspend fun getListCategory(): List<CategoryEntity>

    //Product
    suspend fun insertProduct(product: ProductEntity)
    suspend fun getListProductsByCategory(idcategory: Int): List<ProductEntity>
    suspend fun getListSomeProducts(): List<ProductEntity>
    suspend fun updateProductToFavorite(idProduct: Int, isFavorite: Int)
    suspend fun getListSimilarProducts(idProduct: Int, nameKey: String): List<ProductEntity>
    suspend fun searchProductsByName(nameKey: String): List<ProductEntity>
    suspend fun getFavoriteProductList(): List<ProductEntity>

    //Order
    suspend fun getListOrders(): List<OrderEntity>
    suspend fun insertNewOrder(order: OrderEntity)

    //OrderByProduct
    suspend fun insertOrderByProduct(orderByProduct: OrderByProductEntity)
    suspend fun getOrderByProductList(): List<BagProductEntity>
    suspend fun deleteProductFromBag(idProduct: Int)
    suspend fun deleteAllOrdersByProducts()

}