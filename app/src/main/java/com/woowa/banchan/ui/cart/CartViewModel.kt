package com.woowa.banchan.ui.cart

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(

): ViewModel() {

    private val _state = MutableStateFlow(CartUiState())
    val state = _state.asStateFlow()

    init {
        getCart()
    }

    private fun getCart() {
        _state.value = state.value.copy(cart = mutableListOf(), isLoading = true, errorMessage = "")

        //TODO: Room Repository 와 연결
        _state.value = state.value.copy(cart = testCartItem, isLoading = false, errorMessage = "")
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

    fun deleteCartItem(id: Long = -1L) {
        if (id == -1L)
            _state.value = state.value.copy(
                cart = state.value.cart.filter { !it.checked }.toMutableList(),
                isLoading = false,
                errorMessage = ""
            )
        else
            _state.value = state.value.copy(
                cart = state.value.cart.filter { it.id != id }.toMutableList(),
                isLoading = false,
                errorMessage = ""
            )
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
}