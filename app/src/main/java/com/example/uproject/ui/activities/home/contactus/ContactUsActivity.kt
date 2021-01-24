package com.example.uproject.ui.activities.home.contactus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.uproject.R
import com.example.uproject.databinding.ActivityContactUsBinding

class ContactUsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactUsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactUsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}