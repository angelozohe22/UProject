package com.example.uproject.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.uproject.core.Resource
import com.example.uproject.domain.auth.AuthRepository
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class RestorePasswordViewModel(private val repository: AuthRepository): ViewModel() {

    fun restorePassword(email: String) = liveData(Dispatchers.IO){
        emit(Resource.Loading)
        try {
            emit(Resource.Success(repository.restorePassword(email)))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }



}