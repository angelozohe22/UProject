package com.example.uproject.core.aplication

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object Constants {

    //FirebaseAuth
    val FIREBASE_AUTH = FirebaseAuth.getInstance()
    val authInstance = FirebaseAuth.getInstance()

    //Firebase Firestore
    val FIREBASE_FIRESTORE = FirebaseFirestore.getInstance()
    val storeInstance = FirebaseFirestore.getInstance()

    //Firebase Collections
    const val USERS = "Users"

    //Firebase Auth Error
    const val USER_NOT_FOUND = "ERROR_USER_NOT_FOUND"
    const val USER_DISABLED  = "ERROR_USER_DISABLED"
    const val WRONG_PASSWORD = "ERROR_WRONG_PASSWORD"
    const val EMAIL_ALREADY_IN_USE = "ERROR_EMAIL_ALREADY_IN_USE"
    const val INVALID_EMAIL = "ERROR_INVALID_EMAIL"
    const val MISSING_EMAIL = "ERROR_MISSING_EMAIL"
}