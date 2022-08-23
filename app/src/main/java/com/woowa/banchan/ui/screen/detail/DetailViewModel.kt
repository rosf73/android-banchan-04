package com.woowa.banchan.ui.screen.detail

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowa.banchan.domain.entity.DetailProduct
import com.woowa.banchan.domain.exception.NotFoundProductsException
import com.woowa.banchan.domain.usecase.product.GetDetailProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getDetailProductUseCase: GetDetailProductUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(DetailUiState())
    val state = _state.asStateFlow()

    private val _cartEvent = MutableSharedFlow<DetailProduct>()
    val cartEvent = _cartEvent.asSharedFlow()

    private val _quantity = MutableLiveData(1)
    val quantity: LiveData<Int> get() = _quantity

    fun getDetailProduct(hash: String) = viewModelScope.launch {
        if (hash.isBlank()) return@launch

        _state.value =
            state.value.copy(product = DetailProduct.default, isLoading = true, errorMessage = "")
        getDetailProductUseCase(hash).onEach { result ->
            result.onSuccess {
                _state.value = state.value.copy(
                    product = it,
                    isLoading = false,
                    errorMessage = ""
                )
            }.onFailure { exception ->
                when (exception) {
                    is NotFoundProductsException -> {
                        _state.value = state.value.copy(
                            product = DetailProduct.default,
                            isLoading = false,
                            errorMessage = "상품을 찾을 수 없습니다."
                        )
                    }
                    else -> {
                        _state.value = state.value.copy(
                            product = DetailProduct.default,
                            isLoading = false,
                            errorMessage = "에러가 발생했습니다."
                        )
                    }
                }
            }
            initData()
        }.launchIn(this)
    }

    private fun initData() {
        _quantity.postValue(1)
    }

    fun plusQuantity(view: View) {
        _quantity.value = quantity.value!! + 1
    }

    fun minusQuantity(view: View) {
        if (quantity.value!! > 1)
            _quantity.value = quantity.value!! - 1
    }

    fun onOrderEvent() = viewModelScope.launch {
        _cartEvent.emit(state.value.product)
    }
}