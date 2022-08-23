package com.woowa.banchan.ui.screen.main.tabs.soup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import com.woowa.banchan.R
import com.woowa.banchan.databinding.FragmentSoupBinding
import com.woowa.banchan.domain.entity.ProductViewType
import com.woowa.banchan.ui.customview.CartBottomSheet
import com.woowa.banchan.ui.extensions.repeatOnLifecycle
import com.woowa.banchan.ui.extensions.toVisibility
import com.woowa.banchan.ui.screen.main.MainFragment
import com.woowa.banchan.ui.screen.main.tabs.ProductsViewModel
import com.woowa.banchan.ui.screen.main.tabs.adapter.BannerAdapter
import com.woowa.banchan.ui.screen.main.tabs.adapter.CountFilterAdapter
import com.woowa.banchan.ui.screen.main.tabs.adapter.ProductAdapter
import com.woowa.banchan.ui.screen.main.tabs.decoration.ItemDecoration
import com.woowa.banchan.ui.screen.recently.RecentlyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class SoupFragment : Fragment() {

    private var _binding: FragmentSoupBinding? = null
    private val binding: FragmentSoupBinding get() = requireNotNull(_binding)

    private val productsViewModel: ProductsViewModel by viewModels()
    private val recentlyViewModel: RecentlyViewModel by activityViewModels()

    private val gridItemDecoration by lazy { ItemDecoration(0) }
    private val productAdapter by lazy {
        ProductAdapter(
            onClick = { product ->
                (parentFragment as MainFragment).navigateToDetail(
                    product.detailHash,
                    product.title,
                    product.description
                )
                recentlyViewModel.modifyRecently(
                    hash = product.detailHash,
                    name = product.title,
                    description = product.description,
                    imageUrl = product.image,
                    nPrice = product.nPrice,
                    sPrice = product.sPrice,
                    viewedAt = Calendar.getInstance().time.time
                )
            },
            onClickCart = { CartBottomSheet.newInstance(it).show(childFragmentManager, "cart") }
        )
    }

    private val countFilterAdapter by lazy {
        CountFilterAdapter(
            onClickItem = { type -> productsViewModel.getProduct("soup", type) },
        )
    }

    private val concatAdapter by lazy {
        ConcatAdapter(
            BannerAdapter(listOf(getString(R.string.soup_banner_title))),
            countFilterAdapter,
            productAdapter
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSoupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeData()
    }

    private fun initView() {
        binding.lifecycleOwner = viewLifecycleOwner
        productsViewModel.getProduct("soup")
        setGridLayoutManager()
        binding.rvSoup.adapter = concatAdapter
    }

    private fun observeData() {
        viewLifecycleOwner.repeatOnLifecycle {
            launch {
                productsViewModel.state.collectLatest { state ->
                    binding.pbSoup.visibility = state.isLoading.toVisibility()
                    if (state.products.isNotEmpty()) {
                        productAdapter.submitList(state.products)
                        countFilterAdapter.submitTotalCount(state.products.size)
                    }
                }
            }

            launch {
                productsViewModel.sortType.collectLatest { sortType ->
                    countFilterAdapter.setSortType(sortType)
                }
            }
        }
    }

    private fun setGridLayoutManager() {
        binding.rvSoup.addItemDecoration(gridItemDecoration)
        productAdapter.setViewType(ProductViewType.Grid)
        val layoutManager = GridLayoutManager(context, 2)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val adapter = concatAdapter.getWrappedAdapterAndPosition(position).first
                return if (adapter is ProductAdapter) 1 else 2
            }
        }
        binding.rvSoup.layoutManager = layoutManager
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}