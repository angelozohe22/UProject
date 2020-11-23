package com.example.uproject.domain.auth

import com.example.uproject.data.firebase.auth.FirebaseDataSource

class AuthRepositoryImpl(private val firebaseDataSource: FirebaseDataSource): AuthRepository {

    override suspend fun signInWithEmailAndPassword(email: String, password: String): Boolean {
        return firebaseDataSource.signInWithEmailAndPassword(email, password)
    }

    override suspend fun signUpWithEmailAndPassword(username: String, phone: String, email: String, password: String
    ): Boolean {
        return firebaseDataSource.signUpWithEmailAndPassword(username, phone, email, password)
    }

    override suspend fun restorePassword(email: String): Boolean {
        return firebaseDataSource.restorePassword(email)
    }
}