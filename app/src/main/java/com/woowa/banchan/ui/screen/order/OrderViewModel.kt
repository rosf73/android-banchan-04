package com.woowa.banchan.ui.screen.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowa.banchan.domain.entity.Product
import com.woowa.banchan.domain.entity.RecentlyViewed
import com.woowa.banchan.domain.usecase.order.AddOrderUserCase
import com.woowa.banchan.domain.usecase.order.GetOrderInfoUseCase
import com.woowa.banchan.domain.usecase.recentlyviewed.ModifyRecentlyViewedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val getOrderInfoUseCase: GetOrderInfoUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(OrderUiState())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getAllOrder()
    }

    private fun getAllOrder() {
        viewModelScope.launch {
            getOrderInfoUseCase().onEach { result ->
                result.onSuccess {
                    _state.value = _state.value.copy(
                        orderInfoList = it,
                        isLoading = false
                    )
                }
                    .onFailure {
                        _eventFlow.emit(UiEvent.ShowToast(it.message))
                    }
            }.launchIn(this)
        }
    }

    fun navigateToDetail(id: Long) {
        viewModelScope.launch {
            _eventFlow.emit(UiEvent.NavigateToOrderDetail(id))
        }
    }

    fun navigateToBack() {
        viewModelScope.launch {
            _eventFlow.emit(UiEvent.NavigateToBack)
        }
    }
}