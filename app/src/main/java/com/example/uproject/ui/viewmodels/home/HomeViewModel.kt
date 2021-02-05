package com.example.uproject.ui.viewmodels.home

import android.util.Log
import androidx.lifecycle.*
import com.example.uproject.data.local.db.entity.CategoryEntity
import com.example.uproject.data.local.db.entity.ProductEntity
import com.example.uproject.domain.model.Category
import com.example.uproject.domain.model.Product
import com.example.uproject.domain.repository.DulcekatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
    private val dulcekatRepository: DulcekatRepository
): ViewModel() {

    private val _listCategoryEntity = MutableLiveData<List<CategoryEntity>>()
    val listCategoryEntity: LiveData<List<CategoryEntity>> get() = _listCategoryEntity

    private val _listProduct = MutableLiveData<List<ProductEntity>>()
    val listProduct: LiveData<List<ProductEntity>> get() = _listProduct

    fun getListCategoryFirebase(){
        viewModelScope.launch(Dispatchers.IO) {
            val listCategory = dulcekatRepository.getListCategoryFirebase()
            insertListCategoryIntoRoom(listCategory)
        }
    }

    private suspend fun insertListCategoryIntoRoom(listCategory: List<Category>) {
        Log.e("err", "paso por insertCat")
        viewModelScope.launch {
            for ((id, categoryEntity) in listCategory.withIndex()) {
                dulcekatRepository.insertCategory(CategoryEntity(id, categoryEntity.name, categoryEntity.image))
            }
            _listCategoryEntity.value = dulcekatRepository.getListCategory()
        }
    }

    fun getListCategory(){
        viewModelScope.launch{
            _listCategoryEntity.value = dulcekatRepository.getListCategory()
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    fun getListProductFirebase(){
        viewModelScope.launch(Dispatchers.IO){
            val listProduct = dulcekatRepository.getListProductFirebase()
            insertListProductIntoRoom(listProduct)
        }
    }

    private suspend fun insertListProductIntoRoom(listProduct: List<Product>){
        Log.e("err", "paso por insertProd")
        viewModelScope.launch {
            for ((id, product) in listProduct.withIndex()) {
                dulcekatRepository.insertProduct(
                    ProductEntity(
                        id,
                        product.name,
                        product.image,
                        product.mark,
                        product.weight,
                        product.price,
                        product.quantity,
                        product.description,
                        product.offer,
                        product.isfavorite,
                        product._idcategory
                    )
                )
            }
            _listProduct.value = dulcekatRepository.getListSomeProducts()
        }
    }

    fun getListProduct(){
        viewModelScope.launch{
            _listProduct.value = dulcekatRepository.getListSomeProducts()
        }
    }

    /*fun fetchCategoryList(){
        viewModelScope.launch(Dispatchers.IO) {
            val data = dulcekatRepository.getListCategory()
            Log.e("insertados", data.size.toString())
        }
    }*/

    /*fun fetchCategoryList() = liveData(Dispatchers.IO) {
        emit(Resource.Loading)
        try {
            emit(Resource.Success(dulcekatRepository.getListCategory()))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }*/

}