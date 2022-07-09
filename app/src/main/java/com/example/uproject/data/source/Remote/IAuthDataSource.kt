package com.example.uproject.data.source.Remote

import com.example.uproject.data.source.Remote.dto.UserDto
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser

interface IAuthDataSource {

    interface IRemoteAuthSource {
        suspend fun signInWithEmailAndPassword(email: String, password: String): FirebaseUser?
        suspend fun signInWithGoogle(credential: AuthCredential): FirebaseUser?
        suspend fun signInWithFacebook(credential: AuthCredential): FirebaseUser?
        suspend fun signUpWithEmailAndPassword(email: String, password: String): FirebaseUser?
        suspend fun retrieveUserData(userId: String)
        suspend fun createNewUserIntoFirebase(userDto: UserDto)
        suspend fun restorePassword(email: String): Boolean
    }

}