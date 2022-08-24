package com.woowa.banchan.ui.screen.orderdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowa.banchan.domain.entity.DeliveryStatus
import com.woowa.banchan.domain.usecase.order.GetOrderInfoUseCase
import com.woowa.banchan.domain.usecase.order.GetOrderLineItemUseCase
import com.woowa.banchan.domain.usecase.order.ModifyOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderDetailViewModel @Inject constructor(
    private val getOrderLineItemUseCase: GetOrderLineItemUseCase,
    private val modifyOrderUseCase: ModifyOrderUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(OrderLineItemUiState())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun getOrderLineItem(orderId: Long) {
        viewModelScope.launch {
            getOrderLineItemUseCase(orderId).onEach { result ->
                result.onSuccess {
                    _state.value = _state.value.copy(
                        orderLineItemList = it,
                        isLoading = false
                    )
                }
                    .onFailure {
                        _eventFlow.emit(UiEvent.ShowToast(it.message))
                    }
            }.launchIn(this)
        }
    }

    fun completeOrder() {
        viewModelScope.launch {
            val orderMap = _state.value.orderLineItemList
            orderMap.entries.forEach {
                modifyOrderUseCase(it.key.copy(status = DeliveryStatus.DONE)).collect { result ->
                    result
                        .onFailure { exception ->
                            _eventFlow.emit(UiEvent.ShowToast(exception.message))
                        }
                }
            }
        }
    }

    fun refreshOrder() {
        viewModelScope.launch {
            _eventFlow.emit(UiEvent.Refresh)
        }
    }

    fun navigateToBack() {
        viewModelScope.launch {
            _eventFlow.emit(UiEvent.NavigateToBack)
        }
    }
}