package com.example.uproject.domain.repository

interface AuthRepository {

    suspend fun signInWithEmailAndPassword(email: String, password: String): Boolean
    suspend fun signUpWithEmailAndPassword(username: String, phone: String, email: String, password: String): Boolean
    suspend fun restorePassword(email: String): Boolean
}