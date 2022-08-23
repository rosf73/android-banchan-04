package com.woowa.banchan.ui.screen.cart.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.woowa.banchan.R
import com.woowa.banchan.domain.entity.Cart
import com.woowa.banchan.extensions.toMoneyInt
import com.woowa.banchan.extensions.toMoneyString

@Composable
fun CartColumn(
    modifier: Modifier = Modifier,
    cart: List<Cart>,
    onItemCheck: (Long) -> Unit,
    onItemUnCheck: (Long) -> Unit,
    onItemDeleteClick: (Long) -> Unit,
    onItemQuantityChanged: (Long, Int) -> Unit,
    onOrderClick: () -> Unit
) {
    var totalPrice by remember { mutableStateOf(cart.sumOf { item -> item.price.toMoneyInt() * item.quantity }) }
    totalPrice = cart.sumOf { item -> item.price.toMoneyInt() * item.quantity }

    Column(modifier = modifier) {
        if (cart.isEmpty())
            CartItemEmpty(modifier = Modifier
                .background(color = colorResource(id = R.color.white))
                .fillMaxWidth())

        else {
            cart.forEach { item ->
                CartItemRow(
                    modifier = Modifier
                        .background(colorResource(R.color.white))
                        .fillMaxWidth(),
                    item = item,
                    onCheck = { onItemCheck(item.id) },
                    onUncheck = { onItemUnCheck(item.id) },
                    onDeleteClick = { onItemDeleteClick(item.id) },
                    onQuantityChanged = { q, isPlus ->
                        onItemQuantityChanged(item.id, q)
                        if (isPlus)
                            totalPrice += item.price.toMoneyInt()
                        else
                            totalPrice -= item.price.toMoneyInt()
                    })
            }

            CartPriceColumn(
                modifier = Modifier.align(Alignment.End),
                totalPrice = totalPrice,
                deliveryFee =
                    if (totalPrice >= 40000) 0
                    else 2500
            )

            Button(
                modifier = Modifier
                    .padding(16.dp, 0.dp)
                    .fillMaxWidth(),
                onClick = onOrderClick,
                enabled = totalPrice >= 10000,
                contentPadding = PaddingValues(16.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(R.color.primary_main),
                    disabledBackgroundColor = colorResource(R.color.primary_disabled)
                )
            ) {
                val orderPrice =
                    if (totalPrice >= 40000) totalPrice
                    else totalPrice + 2500
                Text(text = "${orderPrice.toMoneyString()} 주문하기", color = colorResource(R.color.white))
            }

            if (totalPrice < 40000)
                Text(
                    modifier = Modifier.align(CenterHorizontally),
                    text = "${(40000 - totalPrice).toMoneyString()}을 더 담으면 배송비가 무료!")
        }
    }
}

@Composable
private fun CartItemEmpty(
    modifier: Modifier = Modifier
) {
    Image(
        modifier = modifier
            .padding(0.dp, 100.dp)
            .height(150.dp),
        painter = painterResource(id = R.drawable.empty),
        contentDescription = stringResource(R.string.label_empty)
    )
}