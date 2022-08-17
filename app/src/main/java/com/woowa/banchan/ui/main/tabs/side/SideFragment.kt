package com.woowa.banchan.ui.main.tabs.side

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import com.woowa.banchan.R
import com.woowa.banchan.databinding.FragmentSideBinding
import com.woowa.banchan.domain.entity.ProductViewType
import com.woowa.banchan.ui.customview.CartBottomSheet
import com.woowa.banchan.ui.extensions.repeatOnLifecycle
import com.woowa.banchan.ui.main.MainFragment
import com.woowa.banchan.ui.main.tabs.ProductsViewModel
import com.woowa.banchan.ui.main.tabs.adapter.BannerAdapter
import com.woowa.banchan.ui.main.tabs.adapter.CountFilterAdapter
import com.woowa.banchan.ui.main.tabs.adapter.ProductAdapter
import com.woowa.banchan.ui.main.tabs.decoration.ItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SideFragment : Fragment() {

    private var _binding: FragmentSideBinding? = null
    private val binding: FragmentSideBinding get() = requireNotNull(_binding)
    private val productsViewModel: ProductsViewModel by viewModels()
    private val gridItemDecoration by lazy { ItemDecoration(0) }
    private val productAdapter by lazy {
        ProductAdapter(
            onClick = { product ->
                (parentFragment as MainFragment).navigateToDetail(
                    product.detailHash,
                    product.title,
                    product.description
                )
            },
            onClickCart = { CartBottomSheet(it).show(childFragmentManager, "cart") }
        )
    }

    private val countFilterAdapter by lazy {
        CountFilterAdapter(
            onClickItem = { type -> productsViewModel.getProduct("soup", type) },
        )
    }

    private val concatAdapter by lazy {
        ConcatAdapter(
            BannerAdapter(listOf(getString(R.string.side_banner_title))),
            countFilterAdapter,
            productAdapter
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSideBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeData()
    }

    private fun initView() {
        binding.lifecycleOwner = viewLifecycleOwner
        productsViewModel.getProduct("side")
        setGridLayoutManager()
        binding.rvSide.adapter = concatAdapter
    }

    private fun observeData() {
        viewLifecycleOwner.repeatOnLifecycle {
            productsViewModel.state.collectLatest { state ->
                if (state.products.isNotEmpty()) {
                    productAdapter.submitList(state.products)
                    countFilterAdapter.submitTotalCount(state.products.size)
                }
            }
        }

        viewLifecycleOwner.repeatOnLifecycle {
            productsViewModel.sortType.collectLatest { sortType ->
                countFilterAdapter.setSortType(sortType)
            }
        }
    }

    private fun setGridLayoutManager() {
        binding.rvSide.addItemDecoration(gridItemDecoration)
        productAdapter.setViewType(ProductViewType.Grid)
        val layoutManager = GridLayoutManager(context, 2)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val adapter = concatAdapter.getWrappedAdapterAndPosition(position).first
                return if (adapter is ProductAdapter) 1 else 2
            }
        }
        binding.rvSide.layoutManager = layoutManager
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}