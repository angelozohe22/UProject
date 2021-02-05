package com.example.uproject.ui.viewmodels.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uproject.domain.repository.DulcekatRepository
import kotlinx.coroutines.launch

class PayViewModel(
    private val dulcekatRepository: DulcekatRepository
): ViewModel() {

    fun deleteAllOrdersByProducts(){
        viewModelScope.launch {
            dulcekatRepository.deleteAllOrdersByProducts()
        }
    }


}


