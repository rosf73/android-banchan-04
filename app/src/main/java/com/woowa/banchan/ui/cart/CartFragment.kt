package com.woowa.banchan.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.woowa.banchan.databinding.FragmentCartBinding
import com.woowa.banchan.ui.common.OnBackClickListener
import com.woowa.banchan.utils.toVisibility
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CartFragment: Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding: FragmentCartBinding get() = requireNotNull(_binding)

    private val cartViewModel: CartViewModel by viewModels()

    private val cartItemAdapter = CartItemAdapter()

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

        initListener()
        initData()
    }

    private fun initListener() {
        binding.listener = activity as OnBackClickListener
    }

    private fun initData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                cartViewModel.state.collectLatest { state ->
                    if (state.cart.isNotEmpty()) {
                        setAdapter(state.cart)
                        initBinding()
                        initView()
                    }
                }
            }
        }
    }

    private fun initBinding() {
        with(binding) {

        }
    }

    private fun initView() {
        with(binding) {
            tvCartEmpty.visibility = false.toVisibility()

            rvCart.visibility = true.toVisibility()
            clCartPrice.visibility = true.toVisibility()
            btnOrdering.visibility = true.toVisibility()
        }
    }

    private fun setAdapter(cart: List<TestCartItem>) {
        binding.rvCart.adapter = cartItemAdapter
        cartItemAdapter.submitList(cart)
    }
}