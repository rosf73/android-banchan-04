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
        _state.value = state.value.copy(cart = emptyList(), isLoading = true, errorMessage = "")

        //TODO: Room Repository 와 연결
        _state.value = state.value.copy(cart = testCartItem, isLoading = false, errorMessage = "")
    }

    fun deleteCartItems(ids: List<Long>) {

    }

    fun updateCartItems() {

    }
}