package com.woowa.banchan.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.woowa.banchan.R
import com.woowa.banchan.databinding.FragmentCartBinding
import com.woowa.banchan.ui.OnBackClickListener
import com.woowa.banchan.ui.OnDetailClickListener
import com.woowa.banchan.ui.OnRecentlyClickListener
import com.woowa.banchan.ui.detail.DetailFragment
import com.woowa.banchan.ui.recently.RecentlyFragment
import com.woowa.banchan.ui.recently.RecentlyViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class CartFragment: Fragment(), OnRecentlyClickListener, OnDetailClickListener {

    private var _binding: FragmentCartBinding? = null
    private val binding: FragmentCartBinding get() = requireNotNull(_binding)

    private val cartViewModel: CartViewModel by viewModels()
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
                    navigateToRecently = { navigateToRecently() },
                    onItemClick = {
                        navigateToDetail(it.hash, it.name, it.description)
                        recentlyViewModel.modifyRecently(it.copy(viewedAt = Calendar.getInstance().time.time))
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
            .setCustomAnimations(R.anim.slide_in, 0, 0, R.anim.slide_out)
            .addToBackStack("Cart")
            .add(R.id.fcv_main, RecentlyFragment())
            .commit()
    }

    override fun onStop() {
        super.onStop()
        cartViewModel.updateCartAll()
    }

    override fun navigateToDetail(hash: String, name: String, description: String) {
        parentFragmentManager.popBackStack("Cart", FragmentManager.POP_BACK_STACK_INCLUSIVE)
        parentFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_in, 0, 0, R.anim.slide_out)
            .addToBackStack("Cart")
            .add(R.id.fcv_main, DetailFragment.newInstance(hash, name, description))
            .commit()
    }
}