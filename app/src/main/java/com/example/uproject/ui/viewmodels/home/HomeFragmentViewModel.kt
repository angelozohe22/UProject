package com.example.uproject.ui.viewmodels.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.uproject.core.Resource
import com.example.uproject.data.local.db.category.CategoryEntity
import com.example.uproject.domain.model.Category
import com.example.uproject.domain.repository.DulcekatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class HomeFragmentViewModel(
    private val dulcekatRepository: DulcekatRepository
): ViewModel() {

    init {
        getListCategoryFirebase()
    }

    private fun getListCategoryFirebase(){
        viewModelScope.launch(Dispatchers.IO) {
            Log.e("err", "paso por aqui")
            val listCategory = dulcekatRepository.getListCategoryFirebase()
            insertListCategoryIntoRoom(listCategory)
        }
    }

    private suspend fun insertListCategoryIntoRoom(listCategory: List<Category>) {
        for ((id, categoryEntity) in listCategory.withIndex()) {
            val categoryEntity = CategoryEntity(id, categoryEntity.name, categoryEntity.image)
            dulcekatRepository.insertCategory(categoryEntity)
        }
    }

    fun fetchCategoryList() = liveData(Dispatchers.IO) {
        emit(Resource.Loading)
        try {
            emit(Resource.Success(dulcekatRepository.getListCategory()))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }

}