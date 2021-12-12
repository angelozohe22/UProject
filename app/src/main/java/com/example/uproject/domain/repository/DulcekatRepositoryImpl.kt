package com.example.uproject.domain.repository

import com.example.uproject.data.Remote.home.RemoteFirestoreDataSource
import com.example.uproject.data.local.db.entity.*
import com.example.uproject.data.local.source.LocalDataSource
import com.example.uproject.domain.model.Category
import com.example.uproject.domain.model.Product

class DulcekatRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteFirestoreDataSource: RemoteFirestoreDataSource
): DulcekatRepository {

    override suspend fun insertCategory(category: CategoryEntity) {
        localDataSource.insertCategory(category)
    }

    override suspend fun getListCategory(): List<CategoryEntity> {
        return localDataSource.getListCategory()
    }

    override suspend fun insertProduct(product: ProductEntity) {
        localDataSource.insertProduct(product)
    }

    override suspend fun getListSomeProducts(): List<ProductEntity> {
        return localDataSource.getListSomeProducts()
    }

    override suspend fun getListProductByCategory(idcategory: Int): List<ProductEntity> {
        return localDataSource.getListProductsByCategory(idcategory)
    }

    override suspend fun updateProductToFavorite(idProduct: Int, isFavorite: Int) {
        localDataSource.updateProductToFavorite(idProduct, isFavorite)
    }

    override suspend fun getListSimilarProducts(idProduct:Int, nameKey: String): List<ProductEntity> {
        return localDataSource.getListSimilarProducts(idProduct, nameKey)
    }

    override suspend fun searchProductsByName(nameKey: String): List<ProductEntity> {
        return localDataSource.searchProductsByName(nameKey)
    }

    override suspend fun getFavoriteProductList(): List<ProductEntity> {
        return localDataSource.getFavoriteProductList()
    }

    override suspend fun getListOrders(): List<OrderEntity> {
        return localDataSource.getListOrders()
    }

    override suspend fun insertNewOrder(order: OrderEntity) {
        localDataSource.insertNewOrder(order)
    }

    override suspend fun insertOrderByProduct(orderByProduct: OrderByProductEntity) {
        localDataSource.insertOrderByProduct(orderByProduct)
    }

    override suspend fun getOrderByProductList(): List<BagProductEntity> {
        return localDataSource.getOrderByProductList()
    }

    override suspend fun deleteProductFromBag(idProduct: Int) {
        localDataSource.deleteProductFromBag(idProduct)
    }

    override suspend fun deleteAllOrdersByProducts() {
        localDataSource.deleteAllOrdersByProducts()
    }

    override suspend fun getListCategoryFirebase(): List<Category> {
        return remoteFirestoreDataSource.getListCategory()
    }

    override suspend fun getListProductFirebase(): List<Product> {
        return remoteFirestoreDataSource.getListProducts()
    }
}