package com.example.uproject.data.source.local

import androidx.room.*
import com.example.uproject.data.source.local.entity.*

@Dao
interface DulcekatDao {

    //Category
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CategoryEntity)

    @Query("SELECT * FROM tb_category")
    suspend fun getListCategory(): List<CategoryEntity>

    //Product
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductEntity)

    @Query("SELECT * FROM tb_product WHERE _idcategory = :idcategory")
    suspend fun getListProductsByCategory(idcategory: Int): List<ProductEntity>

    @Query("SELECT * FROM tb_product LIMIT 0,4")
    suspend fun  getListSomeProducts(): List<ProductEntity>

    @Query("UPDATE tb_product SET isfavorite = :isFavorite WHERE idproduct = :idProduct")
    suspend fun updateProductToFavorite(idProduct: Int, isFavorite: Int)

    @Query("Select * from tb_product where name like :nameKey || '%' and idproduct != :idProduct  limit 0,4")
    suspend fun getListSimilarProducts(idProduct: Int, nameKey: String): List<ProductEntity>

    @Query("Select * from tb_product where name like :nameKey || '%' or mark like :nameKey || '%' ")
    suspend fun searchProductsByName(nameKey: String): List<ProductEntity>

    @Query("Select * from tb_product where idproduct like :productId")
    suspend fun getProductById(productId: Int): ProductEntity

    @Query("Select * from tb_product where isfavorite = 1")
    suspend fun getFavoriteProductList(): List<ProductEntity>

    //Order
    @Query("Select * from tb_order")
    suspend fun getListOrders(): List<OrderEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewOrder(order: OrderEntity)

    //Order by product
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderByProduct(orderByProduct: OrderByProductEntity)

    @Query("Select p.idproduct, p.name, p.image, p.mark, p.weight, op.quantity, op.price_by_one from tb_product as p, tb_order_by_product as op where p.idproduct = op.idproduct")
    suspend fun getOrderByProductList(): List<BagProductEntity>

    @Query("Delete from tb_order_by_product where idproduct = :idProduct ")
    suspend fun deleteProductFromBag(idProduct: Int)

    @Query("Delete from tb_order_by_product")
    suspend fun deleteAllOrdersByProducts()




}