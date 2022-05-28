package com.example.uproject.domain.repository

import com.example.uproject.data.source.local.entity.BagProductEntity
import com.example.uproject.data.source.local.entity.OrderByProductEntity
import com.example.uproject.data.source.local.entity.OrderEntity
import com.example.uproject.domain.model.Category
import com.example.uproject.domain.model.Product

interface DulcekatRepository{
    //Local
//    suspend fun setCategoryList(categoryList: List<CategoryEntity>)
//    suspend fun getListCategory(): List<CategoryEntity>
//
//    suspend fun setProductList(productList: List<Product>)

    suspend fun getListSomeProducts(): List<Product>
    suspend fun getListProductByCategory(idcategory: Int): List<Product>
    suspend fun updateProductToFavorite(idProduct: Int, isFavorite: Int)
    suspend fun getListSimilarProducts(idProduct:Int, nameKey: String): List<Product>
    suspend fun searchProductsByName(nameKey: String): List<Product>
    suspend fun getFavoriteProductList(): List<Product>

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