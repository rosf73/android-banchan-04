package com.woowa.banchan.ui.tabs.maindish

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowa.banchan.domain.entity.SortType
import com.woowa.banchan.domain.exception.NotFoundProductsException
import com.woowa.banchan.domain.usecase.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ProductsUiState())
    val state = _state.asStateFlow()

    fun getProduct(type: String, sortType: SortType = SortType.Default) = viewModelScope.launch {
        _state.value = state.value.copy(products = emptyList(), isLoading = true, errorMessage = "")
        getProductsUseCase(type, sortType)
            .onEach { result ->
                result.onSuccess {
                    _state.value = state.value.copy(
                        products = it,
                        isLoading = false,
                        errorMessage = ""
                    )
                }
                    .onFailure { exception ->
                        when (exception) {
                            is NotFoundProductsException -> {
                                _state.value = state.value.copy(
                                    products = emptyList(),
                                    isLoading = false,
                                    errorMessage = "메인요리 상품을 찾을 수 없습니다."
                                )
                            }
                            else -> {
                                _state.value = state.value.copy(
                                    products = emptyList(),
                                    isLoading = false,
                                    errorMessage = "에러가 발생했습니다."
                                )
                            }
                        }
                    }
            }.launchIn(this)
    }
}