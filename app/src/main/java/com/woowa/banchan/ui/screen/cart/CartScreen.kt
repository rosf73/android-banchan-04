package com.woowa.banchan.ui.screen.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.woowa.banchan.R
import com.woowa.banchan.domain.entity.Cart
import com.woowa.banchan.domain.entity.RecentlyViewed
import com.woowa.banchan.extensions.toMoneyInt
import com.woowa.banchan.ui.screen.cart.components.CartCheckBox
import com.woowa.banchan.ui.screen.cart.components.CartItemEmpty
import com.woowa.banchan.ui.screen.cart.components.CartItemRow
import com.woowa.banchan.ui.screen.cart.components.CartOrderButton
import com.woowa.banchan.ui.screen.cart.components.CartPriceColumn
import com.woowa.banchan.ui.screen.cart.components.CheckState
import com.woowa.banchan.ui.screen.cart.components.RecentlyViewedColumn
import com.woowa.banchan.ui.screen.recently.RecentlyViewModel
import kotlin.math.abs

@Composable
fun CartScreen(
    cartViewModel: CartViewModel,
    recentlyViewModel: RecentlyViewModel,
    navigateToRecently: () -> Unit,
    onItemClick: (RecentlyViewed) -> Unit,
    onOrderClick: () -> Unit
) {
    val cartState by cartViewModel.state.collectAsState()
    val recentlyState by recentlyViewModel.state.collectAsState()

    var totalPrice by remember {
        mutableStateOf(
            cartState.cart.sumOf { item ->
                if (item.checked) {
                    item.price.toMoneyInt() * item.quantity
                } else {
                    0
                }
            }
        )
    }

    val (checkState, setCheckState) = remember {
        mutableStateOf(
            if (cartViewModel.isAllUnChecked()) {
                CheckState.UNCHECKED
            } else if (cartViewModel.isAllChecked()) {
                CheckState.CHECKED
            } else {
                CheckState.UNCHECKED_NOT_ALL
            }
        )
    }

    LaunchedEffect(cartState.cart) {
        totalPrice =
            cartState.cart.sumOf { item ->
                if (item.checked) {
                    item.price.toMoneyInt() * item.quantity
                } else {
                    0
                }
            }
    }

    LaunchedEffect(totalPrice) {
        setCheckState(
            if (cartViewModel.isAllUnChecked()) {
                CheckState.UNCHECKED
            } else if (cartViewModel.isAllChecked()) {
                CheckState.CHECKED
            } else {
                CheckState.UNCHECKED_NOT_ALL
            }
        )
    }

    LazyColumn {
        item {
            CartCheckBox(
                modifier = Modifier.fillMaxWidth(),
                state = checkState,
                onCheck = {
                    cartViewModel.checkAll()
                    totalPrice = cartState.cart.sumOf { it.price.toMoneyInt() * it.quantity }
                },
                onUncheck = {
                    cartViewModel.uncheckAll()
                    totalPrice = 0
                },
                onDeleteClick = { cartViewModel.deleteCheckedCarts() }
            )
        }

        if (cartState.cart.isEmpty())
            item {
                CartItemEmpty(
                    modifier = Modifier
                        .background(color = colorResource(id = R.color.white))
                        .fillMaxWidth()
                )
            }
        else {
            itemsIndexed(
                items = cartState.cart,
                key = { index: Int, cart: Cart ->
                    cart.id
                }
            ) { index: Int, item: Cart ->
                CartItemRow(
                    modifier = Modifier
                        .background(colorResource(R.color.white))
                        .fillMaxWidth(),
                    item = item,
                    onCheck = {
                        totalPrice += item.quantity * item.price.toMoneyInt()
                        cartViewModel.check(item.id)
                    },
                    onUncheck = {
                        totalPrice -= item.quantity * item.price.toMoneyInt()
                        cartViewModel.uncheck(item.id)
                    },
                    onDeleteClick = {
                        if (item.checked) {
                            totalPrice -= item.quantity * item.price.toMoneyInt()
                        }
                        cartViewModel.deleteCart(item.id)
                    },
                    onQuantityChanged = { quantity, isPlus ->
                        val diffCount = abs(item.quantity - quantity)
                        cartViewModel.updateCart(item.id, quantity)
                        if (item.checked) {
                            if (isPlus) {
                                totalPrice += item.price.toMoneyInt() * diffCount
                            } else {
                                totalPrice -= item.price.toMoneyInt() * diffCount
                            }
                        }
                    }
                )
            }

            item {
                CartPriceColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(align = Alignment.End),
                    totalPrice = totalPrice,
                    deliveryFee = if (totalPrice >= 40000) 0 else 2500
                )
            }

            item {
                CartOrderButton(totalPrice, onOrderClick)
            }
        }

        item {
            RecentlyViewedColumn(
                recentlyList = if (recentlyState.recentlyList.size < 7) {
                    recentlyState.recentlyList
                } else {
                    recentlyState.recentlyList.subList(0, 7)
                },
                navigateToRecently = navigateToRecently,
                onItemClick = onItemClick
            )
        }
    }
}
