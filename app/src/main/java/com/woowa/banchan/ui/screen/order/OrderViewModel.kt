package com.woowa.banchan.ui.screen.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.woowa.banchan.domain.usecase.order.GetOrderInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val getOrderInfoUseCase: GetOrderInfoUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(OrderUiState())
    val state = _state.asStateFlow()

    val data =
        Pager(PagingConfig(pageSize = 10)) { getOrderInfoUseCase() }.flow
            .catch {

            }
            .cachedIn(viewModelScope)
}