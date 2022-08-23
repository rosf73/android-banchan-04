package com.woowa.banchan.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowa.banchan.domain.entity.Cart
import com.woowa.banchan.domain.entity.DeliveryStatus
import com.woowa.banchan.domain.entity.OrderDetailSection.Order
import com.woowa.banchan.domain.usecase.cart.AddCartUseCase
import com.woowa.banchan.domain.usecase.cart.GetCartUseCase
import com.woowa.banchan.domain.usecase.cart.ModifyCartUseCase
import com.woowa.banchan.domain.usecase.cart.RemoveCartUseCase
import com.woowa.banchan.domain.usecase.order.AddOrderUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val addCartUseCase: AddCartUseCase,
    private val getCartUseCase: GetCartUseCase,
    private val removeCartUseCase: RemoveCartUseCase,
    private val modifyCartUseCase: ModifyCartUseCase,
    private val addOrderUserCase: AddOrderUserCase
) : ViewModel() {

    private val _state = MutableStateFlow(CartUiState())
    val state = _state.asStateFlow()

    private val _orderSuccessFlow = MutableSharedFlow<Order>()
    val orderSuccessFlow = _orderSuccessFlow.asSharedFlow()

    init {
        getCart()
    }

    fun addCart(cart: Cart) {
        viewModelScope.launch(Dispatchers.IO) {
            addCartUseCase(cart).onEach { result ->
                result.onFailure {
                    _state.value = state.value.copy(
                        cart = mutableListOf(),
                        isLoading = false,
                        errorMessage = "상품이 존재하지 않습니다."
                    )
                }
            }.launchIn(this)
        }
    }

    private fun getCart() {
        _state.value = state.value.copy(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            getCartUseCase().onEach { result ->
                result.onSuccess {
                    val cartList = it.map { cartMap -> cartMap.value }
                    _state.value = state.value.copy(
                        cart = cartList.toMutableList(),
                        isLoading = false
                    )
                }.onFailure {
                    _state.value = state.value.copy(
                        isLoading = false,
                        errorMessage = ""
                    )
                }
            }.launchIn(this)
        }
    }

    fun isAllChecked(): Boolean = state.value.cart.count { it.checked } == state.value.cart.size
    fun isAllUnChecked(): Boolean = state.value.cart.count { !it.checked } == state.value.cart.size

    fun deleteCart(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            updateCartAll()
            _state.value.cart.find { it.id == id }?.let {
                removeCartUseCase(it)
            }
        }
    }

    fun deleteCartMany() {
        if (isAllUnChecked()) return
        viewModelScope.launch(Dispatchers.IO) {
            updateCartAll()
            _state.value.cart.filter { it.checked }.let {
                removeCartUseCase(*it.toTypedArray())
            }
        }
    }

    private fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            removeCartUseCase(*state.value.cart.toTypedArray())
        }
    }

    fun updateCart(id: Long, quantity: Int) {
        _state.value.cart.map {
            if (it.id == id) it.apply { this.quantity = quantity }
            else it
        }
    }

    fun updateCartAll() {
        viewModelScope.launch(Dispatchers.IO) {
            modifyCartUseCase(*_state.value.cart.toTypedArray())
        }
    }

    fun check(id: Long): Boolean {
        _state.value.cart.map {
            if (it.id == id) it.apply { checked = true }
            else it
        }
        return isAllChecked()
    }

    fun checkAll() {
        _state.value.cart.map { it.apply { checked = true } }
    }

    fun uncheck(id: Long): Boolean {
        _state.value.cart.map {
            if (it.id == id) it.apply { checked = false }
            else it
        }
        return isAllUnChecked()
    }

    fun uncheckAll() {
        _state.value.cart.map { it.apply { checked = false } }
    }

    fun addOrder() {
        viewModelScope.launch {
            val orderedAt = System.currentTimeMillis()
            val orderId = addOrderUserCase(state.value.cart, orderedAt)
            deleteAll()
            _orderSuccessFlow.emit(Order(orderId, orderedAt, DeliveryStatus.START))
        }
    }
}