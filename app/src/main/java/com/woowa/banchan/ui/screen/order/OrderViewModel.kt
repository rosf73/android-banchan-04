package com.woowa.banchan.ui.screen.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.woowa.banchan.domain.usecase.order.GetOrderInfoUseCase
import com.woowa.banchan.domain.usecase.order.GetStartOrderCountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val getOrderInfoUseCase: GetOrderInfoUseCase,
    private val getStartOrderCountUseCase: GetStartOrderCountUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(OrderUiState())
    val state = _state.asStateFlow()

    val data =
        Pager(PagingConfig(pageSize = 10)) { getOrderInfoUseCase() }.flow
            .catch {

            }
            .cachedIn(viewModelScope)

    init {
        getStartOrderCount()
    }

    private fun getStartOrderCount() {
        viewModelScope.launch {
            getStartOrderCountUseCase().onEach { result ->
                result.onSuccess {
                    if (it > 0)
                        _state.value = _state.value.copy(active = true)
                    else
                        _state.value = _state.value.copy(active = false)
                }.onFailure {

                }
            }.launchIn(this)
        }
    }
}