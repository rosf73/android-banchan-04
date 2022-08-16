package com.woowa.banchan.ui.main.tabs.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowa.banchan.domain.exception.NotFoundProductsException
import com.woowa.banchan.domain.usecase.GetPlanUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanViewModel @Inject constructor(
    private val getPlanUseCase: GetPlanUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PlanUiState())
    val state = _state.asStateFlow()

    init {
        getPlan()
    }

    fun getPlan() = viewModelScope.launch {
        _state.value = state.value.copy(plans = emptyList(), isLoading = true, errorMessage = "")
        getPlanUseCase()
            .onEach { result ->
                result.onSuccess {
                    _state.value = state.value.copy(
                        plans = it,
                        isLoading = false,
                        errorMessage = ""
                    )
                }
                    .onFailure { exception ->
                        when (exception) {
                            is NotFoundProductsException -> {
                                _state.value = state.value.copy(
                                    plans = emptyList(),
                                    isLoading = false,
                                    errorMessage = "기획전 상품을 찾을 수 없습니다."
                                )
                            }
                            else -> {
                                _state.value = state.value.copy(
                                    plans = emptyList(),
                                    isLoading = false,
                                    errorMessage = "에러가 발생했습니다."
                                )
                            }
                        }
                    }
            }.launchIn(this)
    }
}