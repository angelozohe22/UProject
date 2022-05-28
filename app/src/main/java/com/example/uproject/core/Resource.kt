package com.example.uproject.core

sealed class Resource<out T: Any> {
    object Loading : Resource<Nothing>()
    data class Success<out T: Any>(val data: T) : Resource<T>()
    data class Failure(val errorMessage: String) : Resource<Nothing>()
}