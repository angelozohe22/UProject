package com.example.uproject.data.repository

import com.example.uproject.data.source.Remote.dto.FavoriteProductDto
import com.example.uproject.data.source.Remote.home.RemoteFirestoreDataSource
import com.example.uproject.data.source.Remote.response.toFavoriteProduct
import com.example.uproject.data.source.local.LocalDataSource
import com.example.uproject.data.source.local.entity.BagProductEntity
import com.example.uproject.data.source.local.entity.OrderByProductEntity
import com.example.uproject.data.source.local.entity.OrderEntity
import com.example.uproject.domain.model.*
import com.example.uproject.domain.repository.DulcekatRepository

class DulcekatRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteFirestoreDataSource: RemoteFirestoreDataSource
): DulcekatRepository {

//    override suspend fun setCategoryList(categoryList: List<CategoryEntity>) {
//        localDataSource.setCategoryList(categoryList)
//    }
//
//    override suspend fun getListCategory(): List<CategoryEntity> {
//        return localDataSource.getListCategory()
//    }
//
//    override suspend fun insertProduct(product: ProductEntity) {
//        localDataSource.insertProduct(product)
//    }

    override suspend fun getListSomeProducts(): List<Product> {
        return localDataSource.getListSomeProducts().map { it.toProduct() }
    }

    override suspend fun getListProductByCategory(idcategory: Int): List<Product> {
        return localDataSource.getListProductsByCategory(idcategory).map { it.toProduct() }
    }

    override suspend fun updateProductToFavorite(idProduct: Int, isFavorite: Int) {
        localDataSource.updateProductToFavorite(idProduct, isFavorite)
    }

    override suspend fun getListSimilarProducts(idProduct:Int, nameKey: String): List<Product> {
        val similarProductList = localDataSource.getListSimilarProducts(idProduct, nameKey)
        return similarProductList.map { it.toProduct() }
    }

    override suspend fun searchProductsByName(nameKey: String): List<Product> {
        return localDataSource.searchProductsByName(nameKey).map { it.toProduct() }
    }

    override suspend fun getFavoriteProductList(): List<FavoriteProduct> {
        return remoteFirestoreDataSource.getFavoriteProductList().map { it.toFavoriteProduct() }
    }

    override suspend fun getProductById(productId: Int): Product {
        return localDataSource.getProductById(productId).toProduct()
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
        val categoryList = remoteFirestoreDataSource.getListCategory()
        val categoryListMapper = categoryList.mapIndexed { idx, category ->
            category.toCategoryEntity(idx)
        }
        localDataSource.setCategoryList(categoryListMapper)
        return categoryList.mapIndexed { idx, categoryResponse -> categoryResponse.toCategory(idx) }
    }

    override suspend fun getListProductFirebase(): List<Product> {
        val productList = remoteFirestoreDataSource.getListProducts()
        val productListMapper = productList.mapIndexed { idx, product ->
            product.toProductEntity(idx)
        }
        localDataSource.setProductList(productListMapper)
        return productList.mapIndexed { idx, product -> product.toProduct(idx) }
    }

    override suspend fun setFavoriteProduct(favoriteProduct: FavoriteProduct) {
        remoteFirestoreDataSource.setFavoriteProduct(favoriteProduct.toFavoriteProductDto())
        println("//// colocando en favoritos el item:: ${favoriteProduct.productId}")
        try {
            localDataSource.updateProductToFavorite(favoriteProduct.productId, 1)
        }catch (t: Throwable){
            println("//// No pudimos anadir a favorito")
        }


    }

    override suspend fun removeFavoriteProduct(favoriteProductId: Int) {
        remoteFirestoreDataSource.removeFavoriteProduct(favoriteProductId)
        println("//// removiendo en favoritos el item:: $favoriteProductId")
        try {
            localDataSource.updateProductToFavorite(favoriteProductId, 0)
        }catch (t: Throwable){
            println("//// No pudimos elimnar a favorito")
        }


    }
}