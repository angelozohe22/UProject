package com.example.uproject.ui.viewmodels.home

import android.util.Log
import androidx.lifecycle.*
import com.example.uproject.core.Resource
import com.example.uproject.data.local.db.entity.ProductEntity
import com.example.uproject.domain.repository.DulcekatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductViewModel(
    private val dulcekatRepository: DulcekatRepository
): ViewModel() {

    private val _productName = MutableLiveData<String>()
    fun setProductName(productName: String){
        _productName.value = productName
    }

    fun fetchListProductByCategory(idCategory: Int) = liveData(Dispatchers.IO) {
        emit(Resource.Loading)
        try{
            emit(Resource.Success(dulcekatRepository.getListProductByCategory(idCategory)))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }

    val fetchListProductByName = _productName.distinctUntilChanged().switchMap { value ->
        liveData(Dispatchers.IO) {
            emit(Resource.Loading)
            try{
                emit(Resource.Success(dulcekatRepository.searchProductsByName(value)))
            }catch (e: Exception){
                emit(Resource.Failure(e))
            }
        }
    }



}