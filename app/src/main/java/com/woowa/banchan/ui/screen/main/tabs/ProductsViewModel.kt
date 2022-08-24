package com.woowa.banchan.ui.screen.main.tabs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowa.banchan.domain.entity.Product
import com.woowa.banchan.domain.entity.ProductViewType
import com.woowa.banchan.domain.entity.RecentlyViewed
import com.woowa.banchan.domain.entity.SortType
import com.woowa.banchan.domain.exception.NotFoundProductsException
import com.woowa.banchan.domain.usecase.product.GetProductsUseCase
import com.woowa.banchan.domain.usecase.recentlyviewed.ModifyRecentlyViewedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val modifyRecentlyViewedUseCase: ModifyRecentlyViewedUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ProductsUiState())
    val state = _state.asStateFlow()

    private val _eventFLow = MutableSharedFlow<ProductUiEvent<Product>>()
    val eventFlow = _eventFLow.asSharedFlow()

    private val _viewMode = MutableStateFlow(ProductViewType.Grid)
    val viewMode = _viewMode.asStateFlow()

    private val _sortType = MutableStateFlow(SortType.Default)
    val sortType = _sortType.asStateFlow()

    fun getProduct(type: String, sortType: SortType = SortType.Default) = viewModelScope.launch {
        setSortType(sortType)
        _state.value = state.value.copy(products = emptyList(), isLoading = true)
        getProductsUseCase(type, sortType)
            .onEach { result ->
                result.onSuccess {
                    _state.value = state.value.copy(
                        products = it,
                        isLoading = false
                    )
                }
                    .onFailure { exception ->
                        when (exception) {
                            is NotFoundProductsException -> {
                                _eventFLow.emit(ProductUiEvent.ShowToast(exception.message))
                            }
                            else -> {
                                _eventFLow.emit(ProductUiEvent.ShowToast(exception.message))
                            }
                        }
                    }
            }.launchIn(this)
    }

    fun navigateToDetail(product: Product) {
        viewModelScope.launch {
            val newRecently = RecentlyViewed(
                product.detailHash,
                product.title,
                product.description,
                product.image,
                product.nPrice,
                product.sPrice,
                Calendar.getInstance().time.time
            )
            modifyRecentlyViewedUseCase(newRecently)
            _eventFLow.emit(ProductUiEvent.NavigateToDetail(product))
        }
    }

    fun navigateToCart(product: Product) {
        viewModelScope.launch {
            _eventFLow.emit(ProductUiEvent.NavigateToCart(product))
        }
    }

    private fun setSortType(sortType: SortType) {
        _sortType.value = sortType
    }

    fun setViewMode(viewMode: ProductViewType) {
        _viewMode.value = viewMode
    }
}
