package com.woowa.banchan.ui.orderdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowa.banchan.domain.entity.DeliveryStatus
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

    private val _refreshEvent = MutableSharedFlow<Unit>()
    val refreshEvent = _refreshEvent.asSharedFlow()

    fun getOrderLineItem(orderId: Long) {
        viewModelScope.launch {
            getOrderLineItemUseCase(orderId).onEach { result ->
                result.onSuccess {
                    _state.value = _state.value.copy(
                        orderLineItemList = it,
                        isLoading = false,
                        errorMessage = ""
                    )
                }
                    .onFailure {
                        _state.value = _state.value.copy(
                            orderLineItemList = mapOf(),
                            isLoading = false,
                            errorMessage = "주문된 상품 불러오기가 실패했습니다."
                        )
                    }
            }.launchIn(this)
        }
    }

    fun modifyOrder() {
        val orderMap = _state.value.orderLineItemList
        viewModelScope.launch {
            orderMap.entries.forEach {
                modifyOrderUseCase(it.key.copy(status = DeliveryStatus.DONE)).collect { result ->
                    result
                        .onFailure {
                            _state.value = _state.value.copy(
                                orderLineItemList = mapOf(),
                                isLoading = false,
                                errorMessage = "배달 상태에 오류가 발생했습니다."
                            )
                        }
                }
            }
        }
    }

    fun refreshOrder() {
        viewModelScope.launch {
            _refreshEvent.emit(Unit)
        }
    }
}