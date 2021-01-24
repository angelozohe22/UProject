package com.example.uproject.ui.activities.home.search

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.uproject.R
import com.example.uproject.common.utils.makeClearableEditText
import com.example.uproject.databinding.ActivityHomeBinding
import com.example.uproject.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private val searchProductAdapter by lazy { SearchProductAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val etSearchView = binding.etSearchView
        etSearchView.requestFocus()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)

        etSearchView.makeClearableEditText()
        etSearchView.setOnEditorActionListener { v, actionId, event ->
            var action = false
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                if(!v.text.isNullOrEmpty()){
                    val inputMethodManager = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(v.windowToken, 0)
                    Toast.makeText(this, "hola", Toast.LENGTH_SHORT).show()
                    action = true
                }else{
                    Toast.makeText(this, "else", Toast.LENGTH_SHORT).show()
                }
            }
            action
        }

        val btnBackSearch = binding.btnBackSearch
        btnBackSearch.setOnClickListener {
            onBackPressed()
        }

    }



}