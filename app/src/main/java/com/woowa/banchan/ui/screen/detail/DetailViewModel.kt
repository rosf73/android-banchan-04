package com.woowa.banchan.ui.screen.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowa.banchan.domain.entity.Cart
import com.woowa.banchan.domain.entity.DetailProduct
import com.woowa.banchan.domain.exception.NotFoundProductsException
import com.woowa.banchan.domain.usecase.cart.AddCartUseCase
import com.woowa.banchan.domain.usecase.product.GetDetailProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getDetailProductUseCase: GetDetailProductUseCase,
    private val addCartUseCase: AddCartUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(DetailUiState())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _cartEvent = MutableSharedFlow<DetailProduct>()
    val cartEvent = _cartEvent.asSharedFlow()

    private val _quantity = MutableLiveData(1)
    val quantity: LiveData<Int> get() = _quantity

    fun getDetailProduct(hash: String) {
        viewModelScope.launch {
            if (hash.isBlank()) return@launch

            _state.value =
                state.value.copy(
                    product = DetailProduct.default,
                    isLoading = true
                )
            getDetailProductUseCase(hash).onEach { result ->
                result.onSuccess {
                    _state.value = state.value.copy(
                        product = it,
                        isLoading = false
                    )
                    initData()
                }.onFailure { exception ->
                    when (exception) {
                        is NotFoundProductsException -> _eventFlow.emit(UiEvent.ShowToast(exception.message))
                        else -> _eventFlow.emit(UiEvent.ShowToast(exception.message))
                    }
                }
            }.launchIn(this)
        }
    }

    private fun initData() {
        _quantity.value = 1
    }

    fun plusQuantity() {
        _quantity.value = quantity.value!! + 1
    }

    fun minusQuantity() {
        if (quantity.value!! > 1)
            _quantity.value = quantity.value!! - 1
    }

    fun onOrderEvent(name: String) {
        viewModelScope.launch {
            val cart = Cart(
                hash = state.value.product.hash,
                name = name,
                imageUrl = state.value.product.thumbs[0],
                quantity = quantity.value!!,
                price = state.value.product.sPrice,
                checked = true
            )
            addCartUseCase(cart).onEach { result ->
                result
                    .onSuccess {
                        _eventFlow.emit(UiEvent.AddToCart(state.value.product))
                    }
                    .onFailure { _eventFlow.emit(UiEvent.ShowToast(it.message)) }
            }.launchIn(this)
        }
    }

    fun navigateToCart() {
        viewModelScope.launch {
            _eventFlow.emit(UiEvent.NavigateToCart)
        }
    }

    fun navigateToOrder() {
        viewModelScope.launch {
            _eventFlow.emit(UiEvent.NavigateToOrder)
        }
    }
}
