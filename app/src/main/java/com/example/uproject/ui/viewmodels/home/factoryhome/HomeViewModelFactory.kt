package com.example.uproject.ui.viewmodels.home.factoryhome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.uproject.domain.repository.DulcekatRepository

class HomeViewModelFactory(private val repository: DulcekatRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(DulcekatRepository::class.java).newInstance(repository)
    }
}