package com.example.uproject.data.firebase.auth

import android.util.Log
import com.example.uproject.common.FirebaseAuth
import com.example.uproject.core.aplication.Constants.USERS
import com.example.uproject.core.aplication.preferences
import com.example.uproject.domain.model.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

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
                        else{ _isSuccessful = false; auth.signOut() }
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
        var newFirebaseUser = auth.currentUser
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            newFirebaseUser = auth.currentUser
            _isSuccessful = task.isSuccessful && newFirebaseUser != null
        }.await()

        if(_isSuccessful){
            newFirebaseUser?.sendEmailVerification()?.await()
            val id = newFirebaseUser?.uid ?: "0"
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
        }
        return _isSuccessful
    }

    override suspend fun restorePassword(email: String): Boolean {
        auth.sendPasswordResetEmail(email).addOnCompleteListener {
            _isSuccessful = it.isSuccessful
        }.addOnFailureListener {
            _isSuccessful = false
        }.await()
        return _isSuccessful
    }


}