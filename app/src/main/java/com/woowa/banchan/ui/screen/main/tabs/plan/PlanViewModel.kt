package com.woowa.banchan.ui.screen.main.tabs.plan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowa.banchan.domain.entity.Product
import com.woowa.banchan.domain.entity.RecentlyViewed
import com.woowa.banchan.domain.exception.NotFoundProductsException
import com.woowa.banchan.domain.usecase.product.GetPlanUseCase
import com.woowa.banchan.domain.usecase.recentlyviewed.ModifyRecentlyViewedUseCase
import com.woowa.banchan.ui.screen.main.tabs.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PlanViewModel @Inject constructor(
    private val getPlanUseCase: GetPlanUseCase,
    private val modifyRecentlyViewedUseCase: ModifyRecentlyViewedUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PlanUiState())
    val state = _state.asStateFlow()

    private val _eventFLow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFLow.asSharedFlow()

    init {
        getPlan()
    }

    fun getPlan() {
        viewModelScope.launch {
            _state.value = state.value.copy(isLoading = true)
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
                                    _eventFLow.emit(UiEvent.ShowToast(exception.message))
                                }
                                else -> {
                                    _eventFLow.emit(UiEvent.ShowToast(exception.message))
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
            _eventFLow.emit(UiEvent.NavigateToDetail(product))
        }
    }

    fun navigateToCart(product: Product) {
        viewModelScope.launch {
            _eventFLow.emit(UiEvent.NavigateToCart(product))
        }
    }
}