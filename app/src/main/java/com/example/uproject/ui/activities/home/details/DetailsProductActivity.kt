package com.example.uproject.ui.activities.home.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.uproject.R
import com.example.uproject.databinding.ActivityDetailsProductBinding

class DetailsProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}