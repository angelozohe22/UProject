package com.example.uproject.ui.modules.home.details

import android.util.Log
import androidx.lifecycle.*
import com.example.uproject.core.Resource
import com.example.uproject.data.source.local.entity.OrderByProductEntity
import com.example.uproject.data.source.local.entity.OrderEntity
import com.example.uproject.domain.model.FavoriteProduct
import com.example.uproject.domain.repository.DulcekatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductDetailsViewModel(
    private val dulcekatRepository: DulcekatRepository
): ViewModel(){

    private val _count = MutableLiveData<Int>(1)
    val count: LiveData<Int> get() = _count

    fun increaseQuantity(){ _count.value = _count.value?.plus(1) ?: 1 }
    fun decreaseQuantity(){ _count.value = _count.value?.minus(1) ?: 1 }

    private val _orders = MutableLiveData<List<OrderEntity>>()
    val orders: LiveData<List<OrderEntity>> get() = _orders

    fun addProductToFavorite(favoriteProduct: FavoriteProduct)= liveData(Dispatchers.IO) {
        emit(Resource.Loading)
        try {
            emit(Resource.Success(dulcekatRepository.setFavoriteProduct(favoriteProduct)))
        }catch (e: Exception){
            emit(Resource.Failure("Ha ocurrido un error añadir::: ${e.localizedMessage}"))
        }
    }

    fun removeProductFromFavorite(productId: Int)= liveData(Dispatchers.IO) {
        emit(Resource.Loading)
        try {
            emit(Resource.Success(dulcekatRepository.removeFavoriteProduct(productId)))
        }catch (e: Exception){
            emit(Resource.Failure("Ha ocurrido un error eliminar::: ${e.localizedMessage}"))
        }
    }

    fun getListSimilarProducts(idProduct:Int, nameKey: String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading)
        try {
            emit(Resource.Success(dulcekatRepository.getListSimilarProducts(idProduct, nameKey)))
        }catch (e: Exception){
            emit(Resource.Failure("e"))
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