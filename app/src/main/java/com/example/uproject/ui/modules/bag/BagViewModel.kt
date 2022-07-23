package com.example.uproject.ui.modules.bag

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.uproject.core.Resource
import com.example.uproject.domain.repository.DulcekatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BagViewModel(
    private val dulcekatRepository: DulcekatRepository
): ViewModel() {

    fun fetchBagProductList() = liveData(Dispatchers.IO) {
        emit(Resource.Loading)
        try {
            emit(Resource.Success(dulcekatRepository.getOrderByProductList()))
        }catch (e: Exception){
            emit(Resource.Failure("errrrrrrrrrrrrr ${e.localizedMessage}"))
        }
    }

    fun deleteProductFromBag(idProduct: Int){
        viewModelScope.launch {
            dulcekatRepository.deleteProductFromBag(idProduct)
        }
    }


}