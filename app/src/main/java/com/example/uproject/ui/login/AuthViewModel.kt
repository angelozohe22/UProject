package com.example.uproject.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.uproject.common.utils.getErrorMessage
import com.example.uproject.core.Resource
import com.example.uproject.domain.repository.AuthRepository
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class AuthViewModel(private val repository: AuthRepository): ViewModel() {

    fun signIn(email: String, password: String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading)
        try {
            emit(Resource.Success(repository.signInWithEmailAndPassword(email, password)))
        }catch (error: Exception){
            emit(Resource.Failure(getErrorMessage(error)))
        }
    }

    fun signInWithGoogle(credential: AuthCredential)= liveData(Dispatchers.IO) {
        emit(Resource.Loading)
        try {
            emit(Resource.Success(repository.signInWithGoogle(credential)))
        }catch (error: Exception){
            emit(Resource.Failure(error.message.orEmpty()))
        }
    }

    fun signUp(
        username: String,
        phone: String,
        email: String,
        password: String
    ) = liveData(Dispatchers.IO) {
        emit(Resource.Loading)
        try{
            emit(Resource.Success(repository.signUpWithEmailAndPassword(username, phone, email, password)))
        }catch (error: Exception){
            emit(Resource.Failure(getErrorMessage(error)))
        }
    }

    fun restorePassword(email: String) = liveData(Dispatchers.IO){
        emit(Resource.Loading)
        try {
            emit(Resource.Success(repository.restorePassword(email)))
        }catch (error: Exception){
            emit(Resource.Failure(getErrorMessage(error)))
        }
    }

}