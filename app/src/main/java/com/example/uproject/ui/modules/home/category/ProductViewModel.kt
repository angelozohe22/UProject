package com.example.uproject.ui.modules.home.category

import androidx.lifecycle.*
import com.example.uproject.core.Resource
import com.example.uproject.domain.repository.DulcekatRepository
import kotlinx.coroutines.Dispatchers

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
            emit(Resource.Failure("e"))
        }
    }

    val fetchListProductByName = _productName.distinctUntilChanged().switchMap { value ->
        liveData(Dispatchers.IO) {
            emit(Resource.Loading)
            try{
                emit(Resource.Success(dulcekatRepository.searchProductsByName(value)))
            }catch (e: Exception){
                emit(Resource.Failure("e"))
            }
        }
    }



}