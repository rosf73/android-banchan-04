package com.woowa.banchan.ui.screen.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.paging.map
import com.google.android.material.tabs.TabLayoutMediator
import com.woowa.banchan.R
import com.woowa.banchan.databinding.FragmentMainBinding
import com.woowa.banchan.domain.entity.DeliveryStatus
import com.woowa.banchan.ui.navigator.OnCartClickListener
import com.woowa.banchan.ui.navigator.OnDetailClickListener
import com.woowa.banchan.ui.navigator.OnOrderClickListener
import com.woowa.banchan.ui.extensions.repeatOnLifecycle
import com.woowa.banchan.ui.screen.cart.CartFragment
import com.woowa.banchan.ui.screen.cart.CartViewModel
import com.woowa.banchan.ui.screen.detail.DetailFragment
import com.woowa.banchan.ui.screen.order.OrderFragment
import com.woowa.banchan.ui.screen.order.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment(), OnDetailClickListener, OnCartClickListener, OnOrderClickListener {

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding get() = requireNotNull(_binding)

    private val cartViewModel: CartViewModel by activityViewModels()
    private val orderViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initListener()
        observeData()
    }

    private fun initView() {
        with(binding) {
            vpOrdering.adapter =
                ViewPagerAdapter(childFragmentManager, lifecycle)
            TabLayoutMediator(tlOrdering, vpOrdering) { tab, position ->
                tab.text = Tab.find(position)
            }.attach()
        }
    }

    private fun initListener() {
        binding.cartClickListener = this
        binding.orderClickListener = this
    }

    private fun observeData() {
        viewLifecycleOwner.repeatOnLifecycle {
            launch {
                cartViewModel.state.collect { state ->
                    binding.cartCount = state.cart.size
                }
            }

            launch {
                orderViewModel.state.collect { state ->
                    binding.active = state.active
                }
            }
        }
    }

    override fun navigateToDetail(hash: String, name: String, description: String) {
        parentFragmentManager.popBackStack("Main", FragmentManager.POP_BACK_STACK_INCLUSIVE)
        parentFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in,
                R.anim.slide_out,
                R.anim.slide_in,
                R.anim.slide_out
            )
            .addToBackStack("Main")
            .add(R.id.fcv_main, DetailFragment.newInstance(hash, name, description))
            .commit()
    }

    override fun navigateToCart() {
        parentFragmentManager.popBackStack("Main", FragmentManager.POP_BACK_STACK_INCLUSIVE)
        parentFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in,
                R.anim.slide_out,
                R.anim.slide_in,
                R.anim.slide_out
            )
            .addToBackStack("Main")
            .add(R.id.fcv_main, CartFragment())
            .commit()
    }

    override fun navigateToOrder() {
        parentFragmentManager.popBackStack("Main", FragmentManager.POP_BACK_STACK_INCLUSIVE)
        parentFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in,
                R.anim.slide_out,
                R.anim.slide_in,
                R.anim.slide_out
            )
            .addToBackStack("Main")
            .add(R.id.fcv_main, OrderFragment())
            .commit()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}