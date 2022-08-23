package com.woowa.banchan.ui

import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.woowa.banchan.R
import com.woowa.banchan.domain.entity.SortType
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
    private val productViewModel: ProductsViewModel by viewModels()

    private lateinit var connectivityObserver: ConnectivityObserver
    private val dialog = LoadingFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectivityObserver = NetworkConnectivityObserver(applicationContext)
        setContentView(R.layout.activity_main)

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

    private fun checkNetwork(isActiveNetwork: Boolean) {
        // FragmentTransactions are committed asynchronously
        supportFragmentManager.executePendingTransactions()

        if (isActiveNetwork) {
            planViewModel.getPlan()
            productViewModel.getProduct(type = "main", sortType = SortType.Default)
            productViewModel.getProduct(type = "soup", sortType = SortType.Default)
            productViewModel.getProduct(type = "side", sortType = SortType.Default)
            if (dialog.isAdded) dialog.stay()
        } else {
            if (!dialog.isAdded) dialog.show(supportFragmentManager, dialog.tag)
            Toast.makeText(
                applicationContext,
                getString(R.string.message_network),
                Toast.LENGTH_LONG
            ).show()
        }
    }
}