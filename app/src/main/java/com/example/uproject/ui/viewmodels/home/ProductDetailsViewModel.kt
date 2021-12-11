package com.example.uproject.ui.viewmodels.home

import android.util.Log
import androidx.lifecycle.*
import com.example.uproject.core.Resource
import com.example.uproject.data.local.db.entity.OrderByProductEntity
import com.example.uproject.data.local.db.entity.OrderEntity
import com.example.uproject.domain.repository.DulcekatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductDetailsViewModel(
    private val dulcekatRepository: DulcekatRepository
): ViewModel(){

    private val _count = MutableLiveData<Int>(1)
    val count: LiveData<Int> get() = _count

    fun increaseQuantity(){ _count.value = _count.value?.plus(1) ?: 1 }
    fun decreaseQuantity(){ _count.value = _count.value?.minus(1) ?: 1 }

    private val _orders = MutableLiveData<List<OrderEntity>>()
    val orders: LiveData<List<OrderEntity>> get() = _orders

    fun updateProductToFavorite(idproduct: Int, isfavorite: Int){
        viewModelScope.launch {
            Log.e("update", "paso por aqui")
            dulcekatRepository.updateProductToFavorite(idproduct, isfavorite)
        }
    }

    fun getListSimilarProducts(idProduct:Int, nameKey: String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading)
        try {
            emit(Resource.Success(dulcekatRepository.getListSimilarProducts(idProduct, nameKey)))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }

    fun insertNewOrder(order: OrderEntity){
        viewModelScope.launch {
            dulcekatRepository.insertNewOrder(order)
        }
    }

    fun fetchListOrders() = liveData(Dispatchers.IO){
        emit(dulcekatRepository.getListOrders())
    }

    fun insertOrderByProduct(orderByProduct: OrderByProductEntity){
        viewModelScope.launch {
            dulcekatRepository.insertOrderByProduct(orderByProduct)
        }
    }

    fun getOrderByProductList(){
        viewModelScope.launch {
            val asd = dulcekatRepository.getOrderByProductList()
            Log.e("orderbypr", asd.toString())
        }
    }




}