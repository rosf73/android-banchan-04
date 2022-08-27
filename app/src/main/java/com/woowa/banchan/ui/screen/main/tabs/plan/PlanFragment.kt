package com.woowa.banchan.ui.screen.main.tabs.plan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ConcatAdapter
import com.woowa.banchan.R
import com.woowa.banchan.databinding.FragmentPlanBinding
import com.woowa.banchan.domain.entity.Product
import com.woowa.banchan.ui.MainActivity
import com.woowa.banchan.ui.customview.CartBottomSheet
import com.woowa.banchan.ui.extensions.repeatOnLifecycle
import com.woowa.banchan.ui.extensions.toVisibility
import com.woowa.banchan.ui.navigator.OnDetailClickListener
import com.woowa.banchan.ui.network.ConnectivityObserver
import com.woowa.banchan.ui.screen.main.MainFragment
import com.woowa.banchan.ui.screen.main.tabs.ProductUiEvent
import com.woowa.banchan.ui.screen.main.tabs.adapter.BannerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlanFragment : Fragment(), OnDetailClickListener {

    private var _binding: FragmentPlanBinding? = null
    private val binding: FragmentPlanBinding get() = requireNotNull(_binding)

    private val planAdapter by lazy {
        PlanAdapter(
            onClick = { product -> planViewModel.navigateToDetail(product) },
            onClickCart = { planViewModel.navigateToCart(it) },
        )
    }

    private val planViewModel: PlanViewModel by activityViewModels()
    private val concatAdapter by lazy {
        ConcatAdapter(
            BannerAdapter(listOf(getString(R.string.plan_banner_title)), true),
            planAdapter
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeData()
    }

    private fun initView() {
        planViewModel.getPlan()
        binding.rvPlan.adapter = concatAdapter
    }

    private fun observeData() {
        viewLifecycleOwner.repeatOnLifecycle {
            launch {
                (requireActivity() as MainActivity).getNetworkFlow().collect {
                    if (it == ConnectivityObserver.Status.Available) {
                        planViewModel.getPlan()
                    }
                }
            }

            launch {
                planViewModel.state.collectLatest { state ->
                    binding.pbPlan.visibility = state.isLoading.toVisibility()
                    binding.rvPlan.isGone = true
                    if (state.plans.isNotEmpty()) {
                        binding.rvPlan.isVisible = true
                        planAdapter.submitList(state.plans)
                    }
                }
            }

            launch {
                planViewModel.eventFlow.collectLatest {
                    when (it) {
                        is ProductUiEvent.ShowToast -> showToastMessage(it.message)
                        is ProductUiEvent.NavigateToDetail -> {
                            navigateToDetail(it.data.detailHash, it.data.title, it.data.description)
                        }
                        is ProductUiEvent.NavigateToCart -> navigateToCart(it.data)
                    }
                }
            }
        }
    }

    private fun showToastMessage(message: String?) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun navigateToDetail(hash: String, name: String, description: String) {
        (parentFragment as MainFragment).navigateToDetail(hash, name, description)
    }

    fun navigateToCart(product: Product) {
        CartBottomSheet.newInstance(product).show(childFragmentManager, "cart")
    }
}
