package com.example.uproject.ui.modules.home

import androidx.lifecycle.*
import com.example.uproject.core.Resource
import com.example.uproject.domain.repository.DulcekatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class HomeViewModel(
    private val dulcekatRepository: DulcekatRepository
): ViewModel() {

    fun getCategoryList() = liveData(Dispatchers.IO){
        emit(Resource.Loading)
        try {
             val categoryList = dulcekatRepository.getListCategoryFirebase()
             withContext(Dispatchers.Main){
                 emit(Resource.Success(categoryList))
             }
        }catch (e: Exception){
            emit(Resource.Failure("Ha ocurrido un error"))
        }
    }

    fun getProductList() = liveData(Dispatchers.IO){
        emit(Resource.Loading)
        try {
            val productList = dulcekatRepository.getListProductFirebase().shuffled().take(4)
            withContext(Dispatchers.Main){
                emit(Resource.Success(productList))
            }
        }catch (e: Exception){
            emit(Resource.Failure("Ha ocurrido un error"))
        }
    }

}