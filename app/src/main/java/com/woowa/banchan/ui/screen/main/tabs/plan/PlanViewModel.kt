package com.woowa.banchan.ui.screen.main.tabs.plan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowa.banchan.domain.entity.Product
import com.woowa.banchan.domain.entity.RecentlyViewed
import com.woowa.banchan.domain.exception.NotFoundProductsException
import com.woowa.banchan.domain.usecase.product.GetPlanUseCase
import com.woowa.banchan.domain.usecase.recentlyviewed.ModifyRecentlyViewedUseCase
import com.woowa.banchan.ui.screen.main.tabs.ProductUiEvent
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
class PlanViewModel @Inject constructor(
    private val getPlanUseCase: GetPlanUseCase,
    private val modifyRecentlyViewedUseCase: ModifyRecentlyViewedUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PlanUiState())
    val state = _state.asStateFlow()

    private val _eventFLow = MutableSharedFlow<ProductUiEvent<Product>>()
    val eventFlow = _eventFLow.asSharedFlow()

    fun getPlan() {
        viewModelScope.launch {
            _state.value = state.value.copy(plans = emptyList(), isLoading = true)
            getPlanUseCase()
                .onEach { result ->
                    result.onSuccess {
                        _state.value = state.value.copy(
                            plans = it,
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
}
