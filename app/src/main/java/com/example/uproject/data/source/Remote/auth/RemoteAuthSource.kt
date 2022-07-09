package com.example.uproject.data.source.Remote.auth

import android.util.Log
import com.example.uproject.common.Constants
import com.example.uproject.common.FirebaseAuth
import com.example.uproject.common.FirebaseFirestore
import com.example.uproject.core.preferences
import com.example.uproject.data.source.Remote.IAuthDataSource
import com.example.uproject.data.source.Remote.dto.UserDto
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class RemoteAuthSource(): IAuthDataSource.IRemoteAuthSource {

    private val firebaseInstance by lazy { FirebaseAuth.getInstance() }
    private val firebaseStoreReference by lazy { FirebaseFirestore.getInstance() }

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): FirebaseUser? {
        firebaseInstance.signInWithEmailAndPassword(email, password).await()
        return firebaseInstance.currentUser
    }

    override suspend fun signInWithGoogle(credential: AuthCredential): FirebaseUser? {
        val result = firebaseInstance.signInWithCredential(credential).await()
        return result.user
    }

    override suspend fun signInWithFacebook(credential: AuthCredential): FirebaseUser? {
        val result = firebaseInstance.signInWithCredential(credential).await()
        return result.user
    }

    override suspend fun retrieveUserData(userId: String) {
        firebaseStoreReference.collection(Constants.TB_USERS)
            .document(userId).get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userDto = task.result?.toObject(UserDto::class.java) ?: UserDto()
                    setUserDataIntoPreferences(userDto, userId)
                }
            }.await()
    }

    override suspend fun createNewUserIntoFirebase(userDto: UserDto){
        firebaseStoreReference.collection(Constants.TB_USERS)
            .document(userDto.id)
            .set(userDto)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    setUserDataIntoPreferences(userDto, userDto.id)
                }
            }.await()
    }

    private fun setUserDataIntoPreferences(user: UserDto, userId: String){
        preferences.apply {
            this.deviceToken = userId
            this.username    = user.username
            this.phone       = user.phone
            this.email       = user.email
        }
    }

    override suspend fun signUpWithEmailAndPassword(email: String, password: String): FirebaseUser? {
        firebaseInstance.createUserWithEmailAndPassword(email, password).await()
        firebaseInstance.currentUser?.sendEmailVerification()?.await()
        return firebaseInstance.currentUser
    }

    override suspend fun restorePassword(email: String): Boolean {
        firebaseInstance.sendPasswordResetEmail(email).await()
        return true
    }
}