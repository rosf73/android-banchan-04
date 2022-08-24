package com.woowa.banchan.ui.screen.recently

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowa.banchan.domain.entity.RecentlyViewed
import com.woowa.banchan.domain.exception.NotFoundProductsException
import com.woowa.banchan.domain.usecase.recentlyviewed.GetAllRecentlyViewedUseCase
import com.woowa.banchan.domain.usecase.recentlyviewed.ModifyRecentlyViewedUseCase
import com.woowa.banchan.ui.screen.main.tabs.ProductUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecentlyViewModel @Inject constructor(
    private val getAllRecentlyViewedUseCase: GetAllRecentlyViewedUseCase,
    private val modifyRecentlyViewedUseCase: ModifyRecentlyViewedUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(RecentlyUiState())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<ProductUiEvent<RecentlyViewed>>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getRecently()
    }

    private fun getRecently() = viewModelScope.launch {
        _state.value = state.value.copy(recentlyList = emptyList(), isLoading = true)

        getAllRecentlyViewedUseCase().onEach { result ->
            result.onSuccess {
                _state.value = state.value.copy(recentlyList = it, isLoading = false)
            }.onFailure { exception ->
                when (exception) {
                    is NotFoundProductsException -> {
                        _eventFlow.emit(ProductUiEvent.ShowToast(exception.message))
                    }
                    else -> {
                        _eventFlow.emit(ProductUiEvent.ShowToast(exception.message))
                    }
                }
            }
        }.launchIn(this)
    }

    fun modifyRecently(recentlyViewed: RecentlyViewed) = viewModelScope.launch {
        modifyRecentlyViewedUseCase(recentlyViewed)
    }

    fun navigateToDetail(recentlyViewed: RecentlyViewed) {
        viewModelScope.launch {
            modifyRecently(recentlyViewed)
            _eventFlow.emit(ProductUiEvent.NavigateToDetail(recentlyViewed))
        }
    }

    fun navigateToCart(recentlyViewed: RecentlyViewed) {
        viewModelScope.launch {
            _eventFlow.emit(ProductUiEvent.NavigateToCart(recentlyViewed))
        }
    }

    fun navigateToBack() {
        viewModelScope.launch {
            _eventFlow.emit(ProductUiEvent.NavigateToBack)
        }
    }
}
