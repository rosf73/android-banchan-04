package com.woowa.banchan.ui.detail

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowa.banchan.domain.entity.DetailProduct
import com.woowa.banchan.domain.exception.NotFoundProductsException
import com.woowa.banchan.domain.usecase.GetDetailProductUseCase
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

    val productName = ObservableField("")
    val productDescription = ObservableField("")

    var productHash = ""

    val quantity = ObservableField(1)

    fun getDetailProduct(hash: String) = viewModelScope.launch {
        _state.value = state.value.copy(product = DetailProduct.default, isLoading = true, errorMessage = "")
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
        }.launchIn(this)
    }

    fun plusQuantity(view: View) {
        quantity.set(quantity.get()?.plus(1))
    }

    fun minusQuantity(view: View) {
        if (quantity.get()!! > 1)
            quantity.set(quantity.get()?.minus(1))
    }

    fun setDetailProductInfo(hash: String, name: String, description: String) {
        productHash = hash
        productName.set(name)
        productDescription.set(description)
    }
}