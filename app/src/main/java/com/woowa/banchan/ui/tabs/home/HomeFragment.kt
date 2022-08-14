package com.woowa.banchan.ui.tabs.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ConcatAdapter
import com.woowa.banchan.R
import com.woowa.banchan.databinding.FragmentHomeBinding
import com.woowa.banchan.ui.main.MainFragment
import com.woowa.banchan.ui.tabs.common.BannerAdapter
import com.woowa.banchan.ui.tabs.common.CartBottomSheet
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment() : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = requireNotNull(_binding)

    private lateinit var concatAdapter: ConcatAdapter
    private lateinit var onClickMenu: OnClickMenu
    private lateinit var planAdapter: PlanAdapter

    private val planViewModel: PlanViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeData()
    }

    private fun initView() {
        setAdapter()
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                planViewModel.state.collectLatest { state ->
                    if (state.plans.isNotEmpty()) {
                        concatAdapter.addAdapter(
                            BannerAdapter(
                                listOf(getString(R.string.plan_banner_title)),
                                true
                            )
                        )

                        planAdapter = PlanAdapter(
                            state.plans,
                            onClick = { product ->
                                (parentFragment as MainFragment).navigateToDetail(product.detailHash, product.title, product.description)
                            },
                            onClickCart = { CartBottomSheet(it).show(childFragmentManager, "cart") }
                        )
                        concatAdapter.addAdapter(planAdapter)
                        binding.rvHome.adapter = concatAdapter
                    }
                }
            }
        }
    }

    private fun setAdapter() {
        concatAdapter = ConcatAdapter()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}