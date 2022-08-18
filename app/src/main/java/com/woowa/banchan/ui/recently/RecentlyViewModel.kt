package com.woowa.banchan.ui.recently

import androidx.lifecycle.ViewModel
import com.woowa.banchan.ui.cart.testCartItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class RecentlyViewModel @Inject constructor(

): ViewModel() {

    private val _state = MutableStateFlow(RecentlyUiState())
    val state = _state.asStateFlow()

    init {
        getRecently()
    }

    private fun getRecently() {
        _state.value = state.value.copy(recentlyList = emptyList(), isLoading = true, errorMessage = "")

        //TODO: Room Repository 와 연결
        _state.value = state.value.copy(recentlyList = testRecentlyList, isLoading = false, errorMessage = "")
    }
}