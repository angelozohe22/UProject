package com.example.uproject.domain.repository

import com.google.firebase.auth.AuthCredential

interface AuthRepository {

    suspend fun signInWithEmailAndPassword(email: String, password: String): Boolean
    suspend fun signInWithGoogle(credentials: AuthCredential): Boolean
    suspend fun signInWithFacebook(credentials: AuthCredential): Boolean
    suspend fun signUpWithEmailAndPassword(username: String, phone: String, email: String, password: String): Boolean
    suspend fun restorePassword(email: String): Boolean
}