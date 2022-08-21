package com.woowa.banchan.ui.cart

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.woowa.banchan.domain.entity.RecentlyViewed
import com.woowa.banchan.ui.cart.components.CartCheckBox
import com.woowa.banchan.ui.cart.components.CartColumn
import com.woowa.banchan.ui.cart.components.CheckState
import com.woowa.banchan.ui.cart.components.RecentlyViewedColumn
import com.woowa.banchan.ui.recently.RecentlyViewModel

@Composable
fun CartScreen(
    cartViewModel: CartViewModel,
    recentlyViewModel: RecentlyViewModel,
    navigateToRecently: () -> Unit,
    onItemClick: (RecentlyViewed) -> Unit
) {
    val cartState by cartViewModel.state.collectAsState()
    val recentlyState by recentlyViewModel.state.collectAsState()

    val (checkState, setCheckState) = remember {
        mutableStateOf(
            if (cartViewModel.isAllUnChecked()) CheckState.UNCHECKED
            else if (cartViewModel.isAllChecked()) CheckState.CHECKED
            else CheckState.UNCHECKED_NOT_ALL
        )
    }

    LaunchedEffect(key1 = cartState.cart) {
        setCheckState(
            if (cartViewModel.isAllUnChecked()) CheckState.UNCHECKED
            else if (cartViewModel.isAllChecked()) CheckState.CHECKED
            else CheckState.UNCHECKED_NOT_ALL
        )
    }

    LazyColumn {
        item {
            CartCheckBox(
                modifier = Modifier.fillMaxWidth(),
                state = checkState,
                onCheck = { cartViewModel.checkAll(); setCheckState(CheckState.CHECKED) },
                onUncheck = { cartViewModel.uncheckAll(); setCheckState(CheckState.UNCHECKED) },
                onDeleteClick = { cartViewModel.deleteCartMany() })
            CartColumn(
                modifier = Modifier.fillMaxWidth(),
                cart = cartState.cart,
                onItemCheck = { id ->
                    cartViewModel.check(id)
                    setCheckState(
                        if (cartViewModel.isAllChecked()) CheckState.CHECKED
                        else CheckState.UNCHECKED_NOT_ALL
                    )
                },
                onItemUnCheck = { id ->
                    cartViewModel.uncheck(id)
                    setCheckState(
                        if (cartViewModel.isAllUnChecked()) CheckState.UNCHECKED
                        else CheckState.UNCHECKED_NOT_ALL
                    )
                },
                onItemDeleteClick = { id ->
                    cartViewModel.deleteCart(id)
                },
                onItemQuantityChanged = { id, quantity ->
                    cartViewModel.updateCart(id, quantity)
                })
            RecentlyViewedColumn(
                recentlyList =
                    if (recentlyState.recentlyList.size < 7) recentlyState.recentlyList
                    else recentlyState.recentlyList.subList(0, 7),
                navigateToRecently = navigateToRecently,
                onItemClick = onItemClick
            )
        }
    }
}