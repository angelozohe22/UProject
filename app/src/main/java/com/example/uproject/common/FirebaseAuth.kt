package com.example.uproject.common

import com.example.uproject.core.aplication.Constants.FIREBASE_AUTH
import com.google.firebase.auth.FirebaseAuth

class FirebaseAuth private constructor() {

    companion object{
        private var INSTANCE: FirebaseAuth? = null
        fun getInstance(): FirebaseAuth{
            val tempInstance = INSTANCE
            if(tempInstance != null) return tempInstance
            val authInstance = FIREBASE_AUTH
            INSTANCE = authInstance
            return authInstance
        }
    }

}