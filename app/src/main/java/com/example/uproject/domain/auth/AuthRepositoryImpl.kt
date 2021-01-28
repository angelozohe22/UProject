package com.example.uproject.domain.auth

import com.example.uproject.data.firebase.auth.FirebaseAuthDataSource

class AuthRepositoryImpl(private val firebaseAuthDataSource: FirebaseAuthDataSource): AuthRepository {

    override suspend fun signInWithEmailAndPassword(email: String, password: String): Boolean {
        return firebaseAuthDataSource.signInWithEmailAndPassword(email, password)
    }

    override suspend fun signUpWithEmailAndPassword(username: String, phone: String, email: String, password: String
    ): Boolean {
        return firebaseAuthDataSource.signUpWithEmailAndPassword(username, phone, email, password)
    }

    override suspend fun restorePassword(email: String): Boolean {
        return firebaseAuthDataSource.restorePassword(email)
    }
}