package com.woowa.banchan.ui.recently

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowa.banchan.domain.entity.DetailProduct
import com.woowa.banchan.domain.exception.NotFoundProductsException
import com.woowa.banchan.domain.usecase.GetAllRecentlyViewedUseCase
import com.woowa.banchan.ui.cart.testCartItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecentlyViewModel @Inject constructor(
    private val getAllRecentlyViewedUseCase: GetAllRecentlyViewedUseCase
): ViewModel() {

    private val _state = MutableStateFlow(RecentlyUiState())
    val state = _state.asStateFlow()

    init {
        getRecently()
    }

    private fun getRecently() = viewModelScope.launch {
        _state.value = state.value.copy(recentlyList = emptyList(), isLoading = true, errorMessage = "")

        getAllRecentlyViewedUseCase().onEach { result ->
            result.onSuccess {
                _state.value = state.value.copy(recentlyList = it, isLoading = false, errorMessage = "")
            }.onFailure { exception ->
                when (exception) {
                    is NotFoundProductsException -> {
                        _state.value = state.value.copy(
                            recentlyList = emptyList(),
                            isLoading = false,
                            errorMessage = "상품을 찾을 수 없습니다."
                        )
                    }
                    else -> {
                        _state.value = state.value.copy(
                            recentlyList = emptyList(),
                            isLoading = false,
                            errorMessage = "에러가 발생했습니다."
                        )
                    }
                }
            }
        }.launchIn(this)
    }
}