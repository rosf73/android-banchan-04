package com.woowa.banchan.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.woowa.banchan.databinding.FragmentCartBinding
import com.woowa.banchan.ui.common.OnBackClickListener

class CartFragment: Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding: FragmentCartBinding get() = requireNotNull(_binding)

    private val cartViewModel: CartViewModel by activityViewModels()

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
        binding.composeCart.setContent {
            CartScreen(cartViewModel)
        }

        initListener()
    }

    private fun initListener() {
        binding.listener = activity as OnBackClickListener
    }
}