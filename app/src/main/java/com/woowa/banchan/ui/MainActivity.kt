package com.woowa.banchan.ui

import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.woowa.banchan.R
import com.woowa.banchan.ui.customview.LoadingFragment
import com.woowa.banchan.ui.main.tabs.home.PlanViewModel
import com.woowa.banchan.ui.network.ConnectivityObserver
import com.woowa.banchan.ui.network.NetworkConnectivityObserver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnBackClickListener {

    private val viewModel: PlanViewModel by viewModels()

    private lateinit var connectivityObserver: ConnectivityObserver
    private val dialog = LoadingFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectivityObserver = NetworkConnectivityObserver(applicationContext)
        setContentView(R.layout.activity_main)

        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        checkNetwork(connectivityManager.activeNetwork != null)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                connectivityObserver.observe().collect {
                    checkNetwork(it == ConnectivityObserver.Status.Available)
                }
            }
        }
    }

    override fun navigateToBack() {
        onBackPressed()
    }

    private fun checkNetwork(isActiveNetwork: Boolean) {
        // FragmentTransactions are committed asynchronously
        supportFragmentManager.executePendingTransactions()

        if (isActiveNetwork) {
            viewModel.getPlan()
            if (dialog.isAdded) dialog.stay()
        } else {
            if (!dialog.isAdded) dialog.show(supportFragmentManager, dialog.tag)
            Toast.makeText(applicationContext, getString(R.string.message_network), Toast.LENGTH_LONG).show()
        }
    }
}