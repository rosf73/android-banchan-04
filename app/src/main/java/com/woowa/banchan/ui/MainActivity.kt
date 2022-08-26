package com.woowa.banchan.ui

import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.findFragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.woowa.banchan.R
import com.woowa.banchan.ui.customview.LoadingFragment
import com.woowa.banchan.ui.navigator.OnBackClickListener
import com.woowa.banchan.ui.network.ConnectivityObserver
import com.woowa.banchan.ui.network.NetworkConnectivityObserver
import com.woowa.banchan.ui.screen.main.MainFragment
import com.woowa.banchan.ui.screen.main.tabs.ProductsViewModel
import com.woowa.banchan.ui.screen.main.tabs.plan.PlanViewModel
import com.woowa.banchan.ui.screen.orderdetail.OrderDetailFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnBackClickListener {

    private val planViewModel: PlanViewModel by viewModels()
    private val productsViewModel: ProductsViewModel by viewModels()

    private lateinit var connectivityObserver: ConnectivityObserver
    private var dialog = LoadingFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectivityObserver = NetworkConnectivityObserver(applicationContext)
        setContentView(R.layout.activity_main)

        supportFragmentManager.findFragmentByTag(DIALOG_TAG)?.let {
            dialog = it as LoadingFragment
        }

        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkNetwork(connectivityManager.activeNetwork != null)
        } else {
            checkNetwork(connectivityManager.isDefaultNetworkActive)
        }
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                connectivityObserver.observe().collect {
                    checkNetwork(it == ConnectivityObserver.Status.Available)
                }
            }
        }
        checkState(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        checkState(intent)
    }

    private fun checkState(intent: Intent?) {
        this.intent = intent
        val orderId = intent?.getLongExtra(getString(R.string.order_id), 0) ?: 0
        if (orderId == 0L) return
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
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

    private fun checkNetwork(isActiveNetwork: Boolean) {
        supportFragmentManager.executePendingTransactions()

        if (isActiveNetwork) {
            if (dialog.isAdded) dialog.dismiss()

            supportFragmentManager.findFragmentById(R.id.fcv_main)?.let { // 현재 보여지는 뷰 다시 그리기
                supportFragmentManager.beginTransaction().detach(it).commit()
                supportFragmentManager.beginTransaction().attach(it).commit()
            }
        } else {
            if (!dialog.isAdded) {
                dialog.show(supportFragmentManager, DIALOG_TAG)
            }
            Toast.makeText(
                applicationContext,
                getString(R.string.message_network),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    companion object {
        private const val DIALOG_TAG = "LOADING"
    }
}
