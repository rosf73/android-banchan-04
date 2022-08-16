package com.woowa.banchan.ui.cart.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.woowa.banchan.R
import com.woowa.banchan.ui.cart.TestCartItem
import com.woowa.banchan.utils.toMoneyInt
import com.woowa.banchan.utils.toMoneyString

@Composable
fun CartColumn(
    modifier: Modifier = Modifier,
    cart: List<TestCartItem>,
    onItemCheck: (Long) -> Unit,
    onItemUnCheck: (Long) -> Unit,
    onItemDeleteClick: (Long) -> Unit,
    onItemQuantityChanged: (Long, Int) -> Unit
) {
    var totalPrice by remember { mutableStateOf(cart.sumOf { item -> item.price.toMoneyInt() * item.quantity }) }
    totalPrice = cart.sumOf { item -> item.price.toMoneyInt() * item.quantity }

    Column(modifier = modifier) {
        if (cart.isEmpty())
            CartItemEmpty(modifier = Modifier.fillMaxWidth())

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
                totalPrice = totalPrice)

            Button(
                modifier = Modifier
                    .padding(16.dp, 0.dp)
                    .fillMaxWidth(),
                onClick = { /*TODO*/ },
                enabled = totalPrice >= 40000,
                contentPadding = PaddingValues(16.dp)
            ) {
                Text(text = "${totalPrice.toMoneyString()} 주문하기")
            }

            if (totalPrice < 40000)
                Text(
                    modifier = Modifier.align(CenterHorizontally),
                    text = "${(40000 - totalPrice).toMoneyString()}을 더 담으면 무료!")
        }
    }
}

@Composable
private fun CartItemEmpty(
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier.padding(0.dp, 100.dp),
        text = stringResource(R.string.cart_empty),
        textAlign = TextAlign.Center)
}