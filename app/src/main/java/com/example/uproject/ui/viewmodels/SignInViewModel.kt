package com.example.uproject.ui.viewmodels

import android.os.storage.StorageManager
import android.util.Log
import androidx.lifecycle.*
import com.example.uproject.core.Resource
import com.example.uproject.domain.auth.AuthRepository
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class SignInViewModel(private val authRepository: AuthRepository) : ViewModel(){

    fun signIn(email: String, password: String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading)
        try {
            emit(Resource.Success(authRepository.signInWithEmailAndPassword(email, password)))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }

}