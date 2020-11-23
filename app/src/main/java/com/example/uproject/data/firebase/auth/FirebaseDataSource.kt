package com.example.uproject.data.firebase.auth

interface FirebaseDataSource {

    suspend fun signInWithEmailAndPassword(email: String, password: String): Boolean
    suspend fun signUpWithEmailAndPassword(username: String, phone: String, email: String, password: String): Boolean
    suspend fun restorePassword(email: String): Boolean
}