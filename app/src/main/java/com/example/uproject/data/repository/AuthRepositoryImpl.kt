package com.example.uproject.data.repository

import android.util.Log
import com.example.uproject.data.source.Remote.IAuthDataSource
import com.example.uproject.data.source.Remote.dto.UserDto
import com.example.uproject.domain.repository.AuthRepository
import com.google.firebase.auth.AuthCredential

class AuthRepositoryImpl(
    private val remoteAuthSource: IAuthDataSource.IRemoteAuthSource
): AuthRepository {
    override suspend fun signInWithEmailAndPassword(email: String, password: String): Boolean {
        remoteAuthSource.signInWithEmailAndPassword(email, password)?.let {
            remoteAuthSource.retrieveUserData(it.uid)
        }
        return true
    }

    override suspend fun signInWithGoogle(credentials: AuthCredential): Boolean {
        remoteAuthSource.signInWithGoogle(credentials)?.let { user ->
            remoteAuthSource.createNewUserIntoFirebase(UserDto(user.uid, user.displayName.orEmpty(), user.phoneNumber.orEmpty(), user.email.orEmpty()))
        }
        return true
    }

    override suspend fun signInWithFacebook(credentials: AuthCredential): Boolean {
        remoteAuthSource.signInWithFacebook(credentials)?.let { user ->
            remoteAuthSource.createNewUserIntoFirebase(UserDto(user.uid, user.displayName.orEmpty(), user.phoneNumber.orEmpty(), user.email.orEmpty()))
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