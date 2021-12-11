package com.example.uproject.common

import com.example.uproject.core.aplication.Constants.FIREBASE_FIRESTORE
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseFirestore private constructor() {

    companion object{
        @Volatile
        private var instance: FirebaseFirestore? = null

        fun getInstance(): FirebaseFirestore =
            instance ?: synchronized(this){
                instance ?: FIREBASE_FIRESTORE.also { instance = it }
            }
    }
}