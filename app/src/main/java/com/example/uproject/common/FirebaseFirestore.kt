package com.example.uproject.common

import com.google.firebase.firestore.FirebaseFirestore

class FirebaseFirestore private constructor() {

    companion object{
        @Volatile
        private var instance: FirebaseFirestore? = null
        fun getInstance(): FirebaseFirestore =
            instance ?: synchronized(this){
                instance ?: FirebaseFirestore.getInstance().also { instance = it }
            }
    }
}