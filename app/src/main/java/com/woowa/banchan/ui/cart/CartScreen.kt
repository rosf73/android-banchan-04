package com.woowa.banchan.ui.cart

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.woowa.banchan.ui.cart.components.CartCheckBox
import com.woowa.banchan.ui.cart.components.CartColumn
import com.woowa.banchan.ui.cart.components.CheckState
import com.woowa.banchan.ui.cart.components.RecentlyViewedColumn
import com.woowa.banchan.ui.recently.RecentlyViewModel

@Composable
fun CartScreen(
    cartViewModel: CartViewModel,
    recentlyViewModel: RecentlyViewModel,
    navigateToRecently: () -> Unit
) {
    val cartState by cartViewModel.state.collectAsState()
    val recentlyState by recentlyViewModel.state.collectAsState()

    var checkState by remember {
        mutableStateOf(
            if (cartViewModel.isAllChecked()) CheckState.CHECKED
            else if (cartViewModel.isAllUnChecked()) CheckState.UNCHECKED
            else CheckState.UNCHECKED_NOT_ALL
        )
    }

    LazyColumn {
        item {
            CartCheckBox(
                modifier = Modifier.fillMaxWidth(),
                state = checkState,
                onCheck = { cartViewModel.check(); checkState = CheckState.CHECKED },
                onUncheck = { cartViewModel.uncheck(); checkState = CheckState.UNCHECKED },
                onDeleteClick = { cartViewModel.deleteCartItem() })
            CartColumn(
                modifier = Modifier.fillMaxWidth(),
                cart = cartState.cart,
                onItemCheck = { id ->
                    cartViewModel.check(id)
                    checkState = if (cartViewModel.isAllChecked()) CheckState.CHECKED
                    else CheckState.UNCHECKED_NOT_ALL
                },
                onItemUnCheck = { id ->
                    cartViewModel.uncheck(id)
                    checkState = if (cartViewModel.isAllUnChecked()) CheckState.UNCHECKED
                    else CheckState.UNCHECKED_NOT_ALL
                },
                onItemDeleteClick = { id ->
                    cartViewModel.deleteCartItem(id)
                    checkState = if (cartViewModel.isAllChecked()) CheckState.CHECKED
                    else if (cartViewModel.isAllUnChecked()) CheckState.UNCHECKED
                    else CheckState.UNCHECKED_NOT_ALL
                },
                onItemQuantityChanged = { id, quantity ->
                    cartViewModel.updateCartItem(id, quantity)
                })
            RecentlyViewedColumn(
                recentlyList =
                    if (recentlyState.recentlyList.size < 7) recentlyState.recentlyList
                    else recentlyState.recentlyList.subList(0, 7),
                navigateToRecently = navigateToRecently
            )
        }
    }
}