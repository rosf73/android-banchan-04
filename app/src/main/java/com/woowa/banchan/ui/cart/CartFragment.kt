package com.woowa.banchan.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.woowa.banchan.R
import com.woowa.banchan.databinding.FragmentCartBinding
import com.woowa.banchan.ui.OnBackClickListener
import com.woowa.banchan.ui.OnRecentlyClickListener
import com.woowa.banchan.ui.recently.RecentlyFragment
import com.woowa.banchan.ui.recently.RecentlyViewModel

class CartFragment: Fragment(), OnRecentlyClickListener {

    private var _binding: FragmentCartBinding? = null
    private val binding: FragmentCartBinding get() = requireNotNull(_binding)

    private val cartViewModel: CartViewModel by activityViewModels()
    private val recentlyViewModel: RecentlyViewModel by activityViewModels()

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
            CartScreen(
                cartViewModel,
                recentlyViewModel,
                navigateToRecently = { navigateToRecently() })
        }

        initListener()
    }

    private fun initListener() {
        binding.listener = activity as OnBackClickListener
    }

    override fun navigateToRecently() {
        parentFragmentManager.popBackStack("Cart", FragmentManager.POP_BACK_STACK_INCLUSIVE)
        parentFragmentManager.beginTransaction()
            .addToBackStack("Cart")
            .replace(R.id.fcv_main, RecentlyFragment())
            .commit()
    }
}