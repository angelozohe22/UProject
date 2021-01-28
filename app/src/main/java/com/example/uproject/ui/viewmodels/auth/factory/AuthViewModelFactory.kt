package com.example.uproject.ui.viewmodels.auth.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.uproject.domain.auth.AuthRepository

class AuthViewModelFactory(private val repository: AuthRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(AuthRepository::class.java).newInstance(repository)
    }
}