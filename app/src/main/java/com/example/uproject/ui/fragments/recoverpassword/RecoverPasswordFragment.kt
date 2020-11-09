package com.example.uproject.ui.fragments.recoverpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.example.uproject.R

class RecoverPasswordFragment: DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_forgot_password, container, false)
    }

    override fun onStart() {
        super.onStart()

        dialog?.apply {
            window?.apply {
                setBackgroundDrawableResource(android.R.color.transparent)
                //clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
                setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
            }
        }

    }


}