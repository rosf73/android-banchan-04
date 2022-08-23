package com.woowa.banchan.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.woowa.banchan.R
import com.woowa.banchan.databinding.FragmentCartBinding
import com.woowa.banchan.ui.OnBackClickListener
import com.woowa.banchan.ui.OnDetailClickListener
import com.woowa.banchan.ui.OnOrderDetailClickListener
import com.woowa.banchan.ui.OnRecentlyClickListener
import com.woowa.banchan.ui.customview.LoadingFragment
import com.woowa.banchan.ui.detail.DetailFragment
import com.woowa.banchan.ui.order.OrderViewModel
import com.woowa.banchan.ui.orderdetail.OrderDetailFragment
import com.woowa.banchan.ui.recently.RecentlyFragment
import com.woowa.banchan.ui.recently.RecentlyViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class CartFragment: Fragment(), OnRecentlyClickListener, OnDetailClickListener, OnOrderDetailClickListener {

    private var _binding: FragmentCartBinding? = null
    private val binding: FragmentCartBinding get() = requireNotNull(_binding)

    private val cartViewModel: CartViewModel by activityViewModels()
    private val recentlyViewModel: RecentlyViewModel by viewModels()
    private val orderViewModel: OrderViewModel by activityViewModels()

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
        val dialog = LoadingFragment()
        binding.composeCart.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                CartScreen(
                    cartViewModel,
                    recentlyViewModel,
                    navigateToRecently = { navigateToRecently() },
                    onItemClick = {
                        navigateToDetail(it.hash, it.name, it.description)
                        recentlyViewModel.modifyRecently(it.copy(viewedAt = Calendar.getInstance().time.time))
                    },
                    onOrderClick = {
                        orderViewModel.addOrder(cartViewModel.state.value.cart) {
                            navigateToOrderDetail(it)
                            cartViewModel.deleteAll()
                        }
                    },
                    onShowLoading = {
                        parentFragmentManager.executePendingTransactions()
                        if (!dialog.isAdded) dialog.show(parentFragmentManager, dialog.tag)
                    },
                    onDismissLoading = {
                        parentFragmentManager.executePendingTransactions()
                        if (dialog.isAdded) dialog.stay()
                    }
                )
            }
        }

        initListener()
    }

    private fun initListener() {
        binding.listener = activity as OnBackClickListener
    }

    override fun navigateToRecently() {
        parentFragmentManager.popBackStack("Cart", FragmentManager.POP_BACK_STACK_INCLUSIVE)
        parentFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in, R.anim.slide_out)
            .addToBackStack("Cart")
            .add(R.id.fcv_main, RecentlyFragment())
            .commit()
    }

    override fun navigateToDetail(hash: String, name: String, description: String) {
        parentFragmentManager.popBackStack("Cart", FragmentManager.POP_BACK_STACK_INCLUSIVE)
        parentFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in, R.anim.slide_out)
            .addToBackStack("Cart")
            .add(R.id.fcv_main, DetailFragment.newInstance(hash, name, description))
            .commit()
    }

    override fun navigateToOrderDetail(id: Long) {
        parentFragmentManager.popBackStack("Cart", FragmentManager.POP_BACK_STACK_INCLUSIVE)
        parentFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in, R.anim.slide_out)
            .addToBackStack("Cart")
            .add(R.id.fcv_main, OrderDetailFragment.newInstance(id))
            .commit()
    }

    override fun onStop() {
        super.onStop()
        cartViewModel.updateCartAll()
    }
}