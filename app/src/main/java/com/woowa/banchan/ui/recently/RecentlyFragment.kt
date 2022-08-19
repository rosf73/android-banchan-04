package com.woowa.banchan.ui.recently

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.woowa.banchan.R
import com.woowa.banchan.databinding.FragmentRecentlyBinding
import com.woowa.banchan.domain.entity.Product
import com.woowa.banchan.ui.OnBackClickListener
import com.woowa.banchan.ui.OnCartClickListener
import com.woowa.banchan.ui.OnDetailClickListener
import com.woowa.banchan.ui.OnItemCartClickListener
import com.woowa.banchan.ui.customview.CartBottomSheet
import com.woowa.banchan.ui.detail.DetailFragment
import com.woowa.banchan.ui.extensions.repeatOnLifecycle
import java.util.*

class RecentlyFragment: Fragment(), OnDetailClickListener, OnItemCartClickListener {

    private var _binding: FragmentRecentlyBinding? = null
    private val binding: FragmentRecentlyBinding get() = requireNotNull(_binding)

    private val recentlyViewModel: RecentlyViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecentlyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
        observeData()
    }

    private fun observeData() {
        viewLifecycleOwner.repeatOnLifecycle {
            recentlyViewModel.state.collect { state ->
                binding.rvRecently.adapter = RecentlyAdapter(
                    state.recentlyList,
                    onClick = {
                        navigateToDetail(it.hash, it.name, it.description)
                        recentlyViewModel.modifyRecently(it.copy(viewedAt = Calendar.getInstance().time.time))
                    },
                    onClickCart = {
//                        navigateToCart(it)
                    }
                )
            }
        }
    }

    private fun initListener() {
        binding.listener = activity as OnBackClickListener
    }

    override fun navigateToDetail(hash: String, name: String, description: String) {
        parentFragmentManager.popBackStack("Recently", FragmentManager.POP_BACK_STACK_INCLUSIVE)
        parentFragmentManager.beginTransaction()
            .addToBackStack("Recently")
            .add(R.id.fcv_main, DetailFragment.newInstance(hash, name, description))
            .commit()
    }

    override fun navigateToCart(product: Product) {
        CartBottomSheet(product).show(childFragmentManager, "cart")
    }
}