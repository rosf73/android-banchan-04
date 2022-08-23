package com.woowa.banchan.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.woowa.banchan.R
import com.woowa.banchan.ui.OnBackClickListener
import com.woowa.banchan.ui.orderdetail.OrderDetailFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnBackClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        this.intent = intent
        val orderId = intent?.getLongExtra(getString(R.string.order_id), 0) ?: 0
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fcv_main, MainFragment())
            .commit()

        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in,
                R.anim.slide_out,
                R.anim.slide_in,
                R.anim.slide_out
            )
            .addToBackStack(null)
            .add(R.id.fcv_main, OrderDetailFragment.newInstance(orderId))
            .commit()
    }

    override fun navigateToBack() {
        onBackPressed()
    }
}