package com.woowa.banchan.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.woowa.banchan.R
import com.woowa.banchan.ui.OnBackClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnBackClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun navigateToMain() {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fcv_main, MainFragment())
            .commit()
    }
}