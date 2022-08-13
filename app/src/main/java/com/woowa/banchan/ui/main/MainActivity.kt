package com.woowa.banchan.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import com.woowa.banchan.R
import com.woowa.banchan.ui.common.OnBackClickListener
import com.woowa.banchan.ui.detail.DetailFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnBackClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun navigateToMain() {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fcv_main, MainFragment())
            .commit()
    }
}