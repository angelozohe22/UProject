package com.example.uproject.ui.viewmodels.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.uproject.core.Resource
import com.example.uproject.domain.auth.AuthRepository
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class SignUpViewModel(private val authRepository: AuthRepository): ViewModel() {

    fun signUp(
        username: String,
        phone: String,
        email: String,
        password: String
    ) = liveData(Dispatchers.IO) {
        emit(Resource.Loading)
        try{
            emit(Resource.Success(authRepository.signUpWithEmailAndPassword(username, phone, email, password)))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }

}