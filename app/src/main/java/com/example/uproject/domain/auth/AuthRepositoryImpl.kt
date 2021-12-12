package com.example.uproject.domain.auth

import com.example.uproject.data.Remote.auth.RemoteAuthDataSource

class AuthRepositoryImpl(private val remoteAuthDataSource: RemoteAuthDataSource): AuthRepository {

    override suspend fun signInWithEmailAndPassword(email: String, password: String): Boolean {
        return remoteAuthDataSource.signInWithEmailAndPassword(email, password)
    }

    override suspend fun signUpWithEmailAndPassword(username: String, phone: String, email: String, password: String
    ): Boolean {
        return remoteAuthDataSource.signUpWithEmailAndPassword(username, phone, email, password)
    }

    override suspend fun restorePassword(email: String): Boolean {
        return remoteAuthDataSource.restorePassword(email)
    }
}