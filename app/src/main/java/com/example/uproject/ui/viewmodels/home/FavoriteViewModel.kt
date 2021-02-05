package com.example.uproject.ui.viewmodels.home

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
            emit(Resource.Failure(e))
        }
    }

}