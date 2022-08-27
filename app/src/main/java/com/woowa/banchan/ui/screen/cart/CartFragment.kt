package com.woowa.banchan.ui.screen.cart

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.woowa.banchan.R
import com.woowa.banchan.databinding.FragmentCartBinding
import com.woowa.banchan.domain.entity.OrderDetailSection.Order
import com.woowa.banchan.ui.extensions.repeatOnLifecycle
import com.woowa.banchan.ui.navigator.OnBackClickListener
import com.woowa.banchan.ui.navigator.OnDetailClickListener
import com.woowa.banchan.ui.navigator.OnOrderDetailClickListener
import com.woowa.banchan.ui.navigator.OnRecentlyClickListener
import com.woowa.banchan.ui.receiver.AlarmBroadcastReceiver
import com.woowa.banchan.ui.screen.detail.DetailFragment
import com.woowa.banchan.ui.screen.orderdetail.OrderDetailFragment
import com.woowa.banchan.ui.screen.recently.RecentlyFragment
import com.woowa.banchan.ui.screen.recently.RecentlyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import java.security.SecureRandom

@AndroidEntryPoint
class CartFragment :
    Fragment(), OnRecentlyClickListener, OnDetailClickListener, OnOrderDetailClickListener {

    private var _binding: FragmentCartBinding? = null
    private val binding: FragmentCartBinding get() = requireNotNull(_binding)

    private val cartViewModel: CartViewModel by activityViewModels()
    private val recentlyViewModel: RecentlyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.composeCart.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                CartScreen(
                    cartViewModel,
                    recentlyViewModel,
                    navigateToRecently = { cartViewModel.navigateToRecentlyViewed() },
                    onItemClick = { cartViewModel.navigateToDetail(it) },
                    onOrderClick = { cartViewModel.addOrder() },
                )
            }
        }

        initView()
        observeData()
    }

    private fun initView() {
        binding.viewModel = cartViewModel
    }

    private fun observeData() {
        viewLifecycleOwner.repeatOnLifecycle {
            cartViewModel.eventFlow.collectLatest {
                when (it) {
                    is UiEvent.NavigateToRecentlyViewed -> navigateToRecently()
                    is UiEvent.NavigateToBack -> (activity as OnBackClickListener).navigateToBack()
                    is UiEvent.NavigateToDetail -> {
                        navigateToDetail(
                            it.product.hash,
                            it.product.name,
                            it.product.description
                        )
                    }
                    is UiEvent.ShowToast -> showToastMessage(it.message)
                    is UiEvent.OrderProduct -> {
                        navigateToOrderDetail(it.order.id)
                        setAlarm(
                            cartViewModel.state.value.cart.filter { cart -> cart.checked }[0].name,
                            cartViewModel.state.value.cart.count { cart -> cart.checked },
                            it.order
                        )
                        cartViewModel.deleteCheckedCarts()
                    }
                }
            }
        }
    }

    override fun navigateToRecently() {
        parentFragmentManager.popBackStack("Cart", FragmentManager.POP_BACK_STACK_INCLUSIVE)
        parentFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in,
                R.anim.slide_out,
                R.anim.slide_in,
                R.anim.slide_out
            )
            .addToBackStack("Cart")
            .replace(R.id.fcv_main, RecentlyFragment())
            .commit()
    }

    override fun navigateToDetail(hash: String, name: String, description: String) {
        parentFragmentManager.popBackStack("Cart", FragmentManager.POP_BACK_STACK_INCLUSIVE)
        parentFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in,
                R.anim.slide_out,
                R.anim.slide_in,
                R.anim.slide_out
            )
            .addToBackStack("Cart")
            .replace(R.id.fcv_main, DetailFragment.newInstance(hash, name, description))
            .commit()
    }

    override fun navigateToOrderDetail(id: Long) {
        parentFragmentManager.popBackStack("Cart", FragmentManager.POP_BACK_STACK_INCLUSIVE)
        parentFragmentManager.beginTransaction()
            .addToBackStack("Cart")
            .add(R.id.fcv_main, OrderDetailFragment.newInstance(id))
            .detach(this@CartFragment)
            .commit()
    }

    private fun setAlarm(foodName: String, count: Int, order: Order) {
        val alarmManager = requireActivity().getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireActivity(), AlarmBroadcastReceiver::class.java).apply {
            putExtra(getString(R.string.order_food_name), foodName)
            putExtra(getString(R.string.order_food_count), count)
            putExtra(getString(R.string.order_id), order.id)
            putExtra(getString(R.string.order_at), order.orderedAt)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            requireContext().applicationContext,
            SecureRandom().nextInt(Int.MAX_VALUE),
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val deliveryTime: Long = 8 * 1000 + SystemClock.elapsedRealtime()
        alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, deliveryTime, pendingIntent)
    }

    private fun showToastMessage(message: String?) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        cartViewModel.updateCartAll()
    }
}
