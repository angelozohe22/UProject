package com.example.uproject.common

import com.example.uproject.core.aplication.Constants
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseFirestore private constructor() {

    companion object{
        private var INSTANCE: FirebaseFirestore? = null
        fun getInstance(): FirebaseFirestore {
            val tempInstance = INSTANCE
            if(tempInstance != null) return tempInstance
            val authInstance = Constants.FIREBASE_FIRESTORE
            INSTANCE = authInstance
            return authInstance
        }
    }
}