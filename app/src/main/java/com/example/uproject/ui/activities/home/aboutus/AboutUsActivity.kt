package com.example.uproject.ui.activities.home.aboutus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.uproject.R
import com.example.uproject.databinding.ActivityAboutUsBinding

class  AboutUsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutUsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutUsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}