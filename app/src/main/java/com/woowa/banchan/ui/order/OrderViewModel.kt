package com.woowa.banchan.ui.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowa.banchan.domain.entity.Cart
import com.woowa.banchan.domain.usecase.order.AddOrderUserCase
import com.woowa.banchan.domain.usecase.order.GetOrderInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val getOrderInfoUseCase: GetOrderInfoUseCase,
    private val addOrderUserCase: AddOrderUserCase
): ViewModel() {

    private val _state = MutableStateFlow(OrderUiState())
    val state = _state.asStateFlow()

    init {
        getAllOrder()
    }

    private fun getAllOrder() {
        viewModelScope.launch {
            getOrderInfoUseCase().onEach { result ->
                result.onSuccess {
                    _state.value = _state.value.copy(
                        orderInfoList = it,
                        isLoading = false,
                        errorMessage = ""
                    )
                }
                    .onFailure {
                        _state.value = _state.value.copy(
                            orderInfoList = emptyList(),
                            isLoading = false,
                            errorMessage = "주문 내역 불러오기가 실패했습니다."
                        )
                    }
            }.launchIn(this)
        }
    }

    fun addOrder(carts: List<Cart>) {
        viewModelScope.launch {
            addOrderUserCase(carts, System.currentTimeMillis())
        }
    }
}