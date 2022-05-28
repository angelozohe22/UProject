package com.example.uproject.common

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object Constants {

    //Firebase Collections
    const val TB_USERS = "Users"

    //Firebase Auth Error
    const val USER_NOT_FOUND = "ERROR_USER_NOT_FOUND"
    const val USER_DISABLED  = "ERROR_USER_DISABLED"
    const val WRONG_PASSWORD = "ERROR_WRONG_PASSWORD"
    const val EMAIL_ALREADY_IN_USE = "ERROR_EMAIL_ALREADY_IN_USE"
    const val INVALID_EMAIL = "ERROR_INVALID_EMAIL"
    const val MISSING_EMAIL = "ERROR_MISSING_EMAIL"
}