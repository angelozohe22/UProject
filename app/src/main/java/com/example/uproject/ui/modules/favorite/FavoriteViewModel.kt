package com.example.uproject.ui.modules.favorite

import androidx.lifecycle.*
import com.example.uproject.core.Resource
import com.example.uproject.domain.repository.DulcekatRepository
import kotlinx.coroutines.Dispatchers

class FavoriteViewModel(
    private val dulcekatRepository: DulcekatRepository
): ViewModel() {

    fun fetchFavoriteProducts() = liveData(Dispatchers.IO) {
        emit(Resource.Loading)
        try {
            emit(Resource.Success(dulcekatRepository.getFavoriteProductList()))
        }catch (e: Exception){
            emit(Resource.Failure("error: ${e.localizedMessage}"))
        }
    }

    fun fetchProductById(productId: Int) = liveData(Dispatchers.IO) {
        emit(Resource.Loading)
        try {
            emit(Resource.Success(dulcekatRepository.getProductById(productId)))
        }catch (e: Exception){
            emit(Resource.Failure("El producto no se encuentra disponible"))
        }
    }



}