package com.woowa.banchan.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowa.banchan.domain.entity.Cart
import com.woowa.banchan.domain.usecase.cart.AddCartUseCase
import com.woowa.banchan.domain.usecase.cart.GetCartUseCase
import com.woowa.banchan.domain.usecase.cart.ModifyCartUseCase
import com.woowa.banchan.domain.usecase.cart.RemoveCartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val addCartUseCase: AddCartUseCase,
    private val getCartUseCase: GetCartUseCase,
    private val removeCartUseCase: RemoveCartUseCase,
    private val modifyCartUseCase: ModifyCartUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CartUiState())
    val state = _state.asStateFlow()

    fun addCart(cart: Cart) = viewModelScope.launch {
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

    fun getCart() = viewModelScope.launch {
        getCartUseCase().onEach { result ->
            result.onSuccess {
                val cartList = it.map { cartMap -> cartMap.value }
                _state.value = state.value.copy(
                    cart = cartList.toMutableList(),
                    isLoading = false,
                    errorMessage = ""
                )
            }
                .onFailure { }
        }.launchIn(this)
    }

    fun isAllChecked(): Boolean = state.value.cart.count { it.checked } == state.value.cart.size

    fun isAllUnChecked(): Boolean = state.value.cart.count { !it.checked } == state.value.cart.size

    fun check(id: Long = -1L) {
        if (id == -1L)
            _state.value = state.value.copy(
                cart = state.value.cart.map { it.apply { checked = true } }.toMutableList(),
                isLoading = false,
                errorMessage = ""
            )
        else
            _state.value = state.value.copy(
                cart = state.value.cart.map {
                    if (it.id == id) it.apply { checked = true }
                    else it
                }.toMutableList(),
                isLoading = false,
                errorMessage = ""
            )
    }

    fun uncheck(id: Long = -1L) {
        if (id == -1L)
            _state.value = state.value.copy(
                cart = state.value.cart.map { it.apply { checked = false } }.toMutableList(),
                isLoading = false,
                errorMessage = ""
            )
        else
            _state.value = state.value.copy(
                cart = state.value.cart.map {
                    if (it.id == id) it.apply { checked = false }
                    else it
                }.toMutableList(),
                isLoading = false,
                errorMessage = ""
            )
    }

    fun deleteCartItem(id: Long = -1L) = viewModelScope.launch(Dispatchers.IO) {
        if (isAllUnChecked()) return@launch
        val deleteList = when (id) {
            -1L -> _state.value.cart.filter { it.checked }.toTypedArray()
            else -> _state.value.cart.filter { it.id == id }.toTypedArray()
        }
        removeCartUseCase(*deleteList)
    }

    fun updateCartItem(id: Long, quantity: Int) {
        _state.value = state.value.copy(
            cart = state.value.cart.map {
                if (it.id == id) it.apply { this.quantity = quantity }
                else it
            }.toMutableList(),
            isLoading = false,
            errorMessage = ""
        )
    }

    fun updateCarts() = viewModelScope.launch(Dispatchers.IO) {
        modifyCartUseCase(*_state.value.cart.toTypedArray())
    }
}