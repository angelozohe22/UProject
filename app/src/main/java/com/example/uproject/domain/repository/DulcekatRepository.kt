package com.example.uproject.domain.repository

import com.example.uproject.data.local.db.entity.*
import com.example.uproject.domain.model.Category
import com.example.uproject.domain.model.Product

interface DulcekatRepository{
    //Local
    suspend fun insertCategory(category: CategoryEntity)
    suspend fun getListCategory(): List<CategoryEntity>

    suspend fun insertProduct(product: ProductEntity)
    suspend fun getListSomeProducts(): List<ProductEntity>
    suspend fun getListProductByCategory(idcategory: Int): List<ProductEntity>
    suspend fun updateProductToFavorite(idProduct: Int, isFavorite: Int)
    suspend fun getListSimilarProducts(idProduct:Int, nameKey: String): List<ProductEntity>
    suspend fun searchProductsByName(nameKey: String): List<ProductEntity>
    suspend fun getFavoriteProductList(): List<ProductEntity>

    suspend fun getListOrders(): List<OrderEntity>
    suspend fun insertNewOrder(order: OrderEntity)

    suspend fun insertOrderByProduct(orderByProduct: OrderByProductEntity)
    suspend fun getOrderByProductList(): List<BagProductEntity>
    suspend fun deleteProductFromBag(idProduct: Int)
    suspend fun deleteAllOrdersByProducts()

    //Firebase
    suspend fun getListCategoryFirebase(): List<Category>
    suspend fun getListProductFirebase(): List<Product>

}