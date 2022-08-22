package com.woowa.banchan.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.woowa.banchan.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnBackClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun navigateToBack() {
        onBackPressed()
    }
}