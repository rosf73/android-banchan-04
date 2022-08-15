package com.woowa.banchan.ui.cart

import androidx.compose.runtime.mutableStateListOf
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

    private val deleteList = mutableStateListOf<Long>()

    init {
        getCart()
    }

    private fun getCart() {
        _state.value = state.value.copy(cart = mutableListOf(), recentlyList = emptyList(), isLoading = true, errorMessage = "")

        //TODO: Room Repository 와 연결
        _state.value = state.value.copy(cart = testCartItem, recentlyList = testRecentlyList, isLoading = false, errorMessage = "")
        deleteList.addAll(testCartItem.map { it.id })
    }

    fun isAllChecked(): Boolean = deleteList.size == state.value.cart.size

    fun addDeleteList(id: Long = -1L) {
        if (id == -1L)
            deleteList.addAll(state.value.cart.map { it.id })
        else
            deleteList.add(id)
    }

    fun removeDeleteList(id: Long = -1L) {
        if (id == -1L)
            deleteList.clear()
        else
            deleteList.remove(id)
    }

    fun deleteCartItems() {
        _state.value = state.value.copy(
            cart = state.value.cart.filter { !deleteList.contains(it.id) }.toMutableList(),
            recentlyList = state.value.recentlyList,
            isLoading = false,
            errorMessage = ""
        )
        removeDeleteList()
    }

    fun updateCartItem(id: Long, quantity: Int) {
        _state.value.cart.forEach {
            if (it.id == id) {
                it.quantity = quantity
                return@forEach
            }
        }
    }
}