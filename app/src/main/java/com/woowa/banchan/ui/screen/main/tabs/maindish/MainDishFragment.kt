package com.woowa.banchan.ui.screen.main.tabs.maindish

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.woowa.banchan.R
import com.woowa.banchan.databinding.FragmentMaindishBinding
import com.woowa.banchan.domain.entity.Product
import com.woowa.banchan.domain.entity.ProductViewType
import com.woowa.banchan.ui.customview.CartBottomSheet
import com.woowa.banchan.ui.extensions.repeatOnLifecycle
import com.woowa.banchan.ui.extensions.toVisibility
import com.woowa.banchan.ui.navigator.OnDetailClickListener
import com.woowa.banchan.ui.screen.main.MainFragment
import com.woowa.banchan.ui.screen.main.tabs.ProductsViewModel
import com.woowa.banchan.ui.screen.main.tabs.ProductUiEvent
import com.woowa.banchan.ui.screen.main.tabs.adapter.BannerAdapter
import com.woowa.banchan.ui.screen.main.tabs.adapter.ProductAdapter
import com.woowa.banchan.ui.screen.main.tabs.adapter.TypeFilterAdapter
import com.woowa.banchan.ui.screen.main.tabs.decoration.ItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainDishFragment : Fragment(), OnDetailClickListener {

    private var _binding: FragmentMaindishBinding? = null
    private val binding: FragmentMaindishBinding get() = requireNotNull(_binding)

    private val productsViewModel: ProductsViewModel by viewModels()

    private val gridItemDecoration by lazy { ItemDecoration(0) }
    private val verticalItemDecoration by lazy { ItemDecoration(1) }
    private val productAdapter by lazy {
        ProductAdapter(
            onClick = { product -> productsViewModel.navigateToDetail(product) },
            onClickCart = { productsViewModel.navigateToCart(it) }
        )
    }

    private val concatAdapter by lazy {
        ConcatAdapter(
            BannerAdapter(listOf(getString(R.string.maindish_banner_title))),
            typeFilterAdapter,
            productAdapter
        )
    }

    private val typeFilterAdapter by lazy {
        TypeFilterAdapter(
            onClickItem = { type -> productsViewModel.getProduct("main", type) },
            onChangeType = { productsViewModel.setViewMode(it) }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMaindishBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeData()
    }

    private fun initView() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.rvMainDish.adapter = concatAdapter
    }

    private fun observeData() {
        viewLifecycleOwner.repeatOnLifecycle {
            launch {
                productsViewModel.state.collectLatest { state ->
                    binding.pbMainDish.visibility = state.isLoading.toVisibility()
                    if (state.products.isNotEmpty()) {
                        productAdapter.submitList(state.products)
                    }
                }
            }

            launch {
                productsViewModel.viewMode.collectLatest { viewMode ->
                    typeFilterAdapter.setViewMode(viewMode)
                    when (viewMode) {
                        ProductViewType.Grid -> setGridLayoutManager()
                        ProductViewType.Vertical -> setLinearLayoutManager()
                    }
                }
            }

            launch {
                productsViewModel.sortType.collectLatest { sortType ->
                    typeFilterAdapter.setSortType(sortType)
                }
            }

            launch {
                productsViewModel.eventFlow.collectLatest {
                    when (it) {
                        is ProductUiEvent.ShowToast -> showToastMessage(it.message)
                        is ProductUiEvent.NavigateToDetail -> navigateToDetail(
                            it.data.detailHash,
                            it.data.title,
                            it.data.description
                        )
                        is ProductUiEvent.NavigateToCart -> navigateToCart(it.data)
                    }
                }
            }
        }
    }

    private fun setGridLayoutManager() {
        productAdapter.setViewType(ProductViewType.Grid)
        binding.rvMainDish.removeItemDecoration(verticalItemDecoration)
        binding.rvMainDish.addItemDecoration(gridItemDecoration)
        val layoutManager = GridLayoutManager(context, 2)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val adapter = concatAdapter.getWrappedAdapterAndPosition(position).first
                return if (adapter is ProductAdapter) 1 else 2
            }
        }
        binding.rvMainDish.layoutManager = layoutManager
    }

    private fun setLinearLayoutManager() {
        productAdapter.setViewType(ProductViewType.Vertical)
        binding.rvMainDish.removeItemDecoration(gridItemDecoration)
        binding.rvMainDish.addItemDecoration(verticalItemDecoration)
        binding.rvMainDish.layoutManager = LinearLayoutManager(context)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun showToastMessage(message: String?) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun navigateToDetail(hash: String, name: String, description: String) {
        (parentFragment as MainFragment).navigateToDetail(hash, name, description)
    }

    fun navigateToCart(product: Product) {
        CartBottomSheet.newInstance(product).show(childFragmentManager, "cart")
    }
}