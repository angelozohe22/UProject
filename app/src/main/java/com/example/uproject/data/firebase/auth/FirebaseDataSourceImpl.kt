package com.example.uproject.data.firebase.auth

import android.util.Log
import com.example.uproject.common.FirebaseAuth
import com.example.uproject.core.aplication.Constants.USERS
import com.example.uproject.core.aplication.preferences
import com.example.uproject.domain.model.User
import com.google.firebase.auth.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class FirebaseDataSourceImpl: FirebaseDataSource {

    private val auth by lazy { FirebaseAuth.getInstance() }
    private val dbReference by lazy { FirebaseFirestore.getInstance() }
    private var _isSuccessful: Boolean = false

    override suspend fun signInWithEmailAndPassword(email: String, password: String): Boolean {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful){
                    val firebaseUser = auth.currentUser
                    firebaseUser?.let { user ->
                        preferences.deviceToken = user.uid
                        if(user.isEmailVerified) { _isSuccessful = true }
                        else{ _isSuccessful = false; auth.signOut()}
                    } ?: run {
                        Log.e("Error", "Entro al run por alguna razon y que firebaseUser es null")
                        _isSuccessful = false
                    }
                }
            }.addOnFailureListener {
                _isSuccessful = false
            }.await()

        dbReference.collection(USERS)
            .document(preferences.deviceToken ?: "0").get().addOnCompleteListener {
                if(it.isSuccessful){
                    val snapshot = it.result
                    val user = snapshot?.toObject(User::class.java)
                    user?.let {
                        preferences.apply {
                            this.username  = it.username
                            this.phone     = it.phone
                            this.email     = it.email
                        }
                    }
                }
            }.await()
        return _isSuccessful
    }

    override suspend fun signUpWithEmailAndPassword( username: String, phone: String, email: String, password: String): Boolean {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            _isSuccessful = task.isSuccessful
        }.addOnFailureListener {
            _isSuccessful = false
        }.await()

        sendEmailVerification()

        val id = auth.currentUser?.uid ?: "0"
        val user = User(id, username, phone, email)
        dbReference.collection(USERS).document(id).set(user).addOnCompleteListener {
            if(it.isSuccessful){
                preferences.apply {
                    this.username   = username
                    this.phone      = phone
                    this.email      = email
                }
            }
            auth.signOut()
        }.await()
        return _isSuccessful
    }

    override suspend fun restorePassword(email: String): Boolean {
        auth.sendPasswordResetEmail(email).addOnCompleteListener {
            Log.e("TAG", auth.currentUser.toString())
            Log.e("TAG", auth.currentUser?.isEmailVerified.toString())
            _isSuccessful = it.isSuccessful
        }.addOnFailureListener {
            _isSuccessful = false
        }.await()
        return _isSuccessful
    }

    private suspend fun sendEmailVerification(){
        val firebaseUser = auth.currentUser
        firebaseUser?.let{
            it.sendEmailVerification().addOnFailureListener {
                Log.e("sendEmailVerification", it.cause.toString())
                Log.e("sendEmailVerification", it.message.toString())
            }.await()
        }
    }


}