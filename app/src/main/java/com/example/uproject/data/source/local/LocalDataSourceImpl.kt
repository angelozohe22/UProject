package com.example.uproject.data.source.local

import com.example.uproject.data.source.helpers.DulcekatDataBase
import com.example.uproject.data.source.local.entity.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalDataSourceImpl(private val dulcekatDataBase: DulcekatDataBase): LocalDataSource {

    override suspend fun setCategoryList(categoryList: List<CategoryEntity>) {
        withContext(Dispatchers.IO){
            categoryList.forEach {
                dulcekatDataBase.dulcekatDao().insertCategory(it)
            }
        }
    }

    override suspend fun getListCategory(): List<CategoryEntity> {
        return dulcekatDataBase.dulcekatDao().getListCategory()
    }

    override suspend fun setProductList(productList: List<ProductEntity>) {
        withContext(Dispatchers.IO){
            productList.forEach {
                dulcekatDataBase.dulcekatDao().insertProduct(it)
            }
        }
    }

    override suspend fun getListProductsByCategory(idcategory: Int): List<ProductEntity> {
        return dulcekatDataBase.dulcekatDao().getListProductsByCategory(idcategory)
    }

    override suspend fun getListSomeProducts(): List<ProductEntity> {
        return dulcekatDataBase.dulcekatDao().getListSomeProducts()
    }

    override suspend fun updateProductToFavorite(idProduct: Int, isFavorite: Int) {
        dulcekatDataBase.dulcekatDao().updateProductToFavorite(idProduct, isFavorite)
    }

    override suspend fun getListSimilarProducts(idProduct:Int, nameKey: String): List<ProductEntity> {
        return dulcekatDataBase.dulcekatDao().getListSimilarProducts(idProduct, nameKey)
    }

    override suspend fun searchProductsByName(nameKey: String): List<ProductEntity> {
        return dulcekatDataBase.dulcekatDao().searchProductsByName(nameKey)
    }

    override suspend fun getFavoriteProductList(): List<ProductEntity> {
        return dulcekatDataBase.dulcekatDao().getFavoriteProductList()
    }

    override suspend fun getListOrders(): List<OrderEntity> {
        return dulcekatDataBase.dulcekatDao().getListOrders()
    }

    override suspend fun insertNewOrder(order: OrderEntity) {
        dulcekatDataBase.dulcekatDao().insertNewOrder(order)
    }

    override suspend fun insertOrderByProduct(orderByProduct: OrderByProductEntity) {
        dulcekatDataBase.dulcekatDao().insertOrderByProduct(orderByProduct)
    }

    override suspend fun getOrderByProductList(): List<BagProductEntity> {
        return dulcekatDataBase.dulcekatDao().getOrderByProductList()
    }

    override suspend fun deleteProductFromBag(idProduct: Int) {
        return dulcekatDataBase.dulcekatDao().deleteProductFromBag(idProduct)
    }

    override suspend fun deleteAllOrdersByProducts() {
        return dulcekatDataBase.dulcekatDao().deleteAllOrdersByProducts()
    }
}