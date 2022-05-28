package com.example.uproject.data.repository

import com.example.uproject.data.source.Remote.IAuthDataSource
import com.example.uproject.data.source.Remote.dto.UserDto
import com.example.uproject.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val remoteAuthSource: IAuthDataSource.IRemoteAuthSource
): AuthRepository {
    override suspend fun signInWithEmailAndPassword(email: String, password: String): Boolean {
        remoteAuthSource.signInWithEmailAndPassword(email, password)?.let {
            remoteAuthSource.retrieveUserData(it.uid)
        }
        return true
    }

    override suspend fun signUpWithEmailAndPassword(
        username: String,
        phone: String,
        email: String,
        password: String
    ): Boolean {
       remoteAuthSource.signUpWithEmailAndPassword(email, password)?.let {
            remoteAuthSource.createNewUserIntoFirebase(UserDto(it.uid, username, phone, email))
        }
        return true
    }

    override suspend fun restorePassword(email: String): Boolean {
        return remoteAuthSource.restorePassword(email)
    }
}