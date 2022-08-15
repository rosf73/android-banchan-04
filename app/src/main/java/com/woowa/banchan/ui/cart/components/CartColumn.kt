package com.woowa.banchan.ui.cart.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
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
    onDeleteClick: (Long) -> Unit
) {
    Column(modifier = modifier) {
        if (cart.isEmpty())
            CartItemEmptyRow(modifier = Modifier.fillMaxWidth())

        else {
            cart.forEach { item ->
                CartItemRow(
                    modifier = Modifier.fillMaxWidth(),
                    item = item,
                    onCheck = {},
                    onUncheck = {})
            }
            CartPriceColumn(modifier = Modifier.align(Alignment.End))
        }
    }
}

@Composable
private fun CartItemEmptyRow(
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier.padding(0.dp, 100.dp),
        text = stringResource(R.string.cart_empty),
        textAlign = TextAlign.Center)
}

@Composable
private fun CartItemRow(
    modifier: Modifier = Modifier,
    item: TestCartItem,
    onCheck: () -> Unit,
    onUncheck: () -> Unit
) {
    val (isChecked, setIsChecked) = remember { mutableStateOf(true) }

    Row(modifier = modifier
        .clickable {
            if (isChecked) {
                onUncheck()
                setIsChecked(false)
            } else {
                onCheck()
                setIsChecked(true)
            }
        }
    ) {
        Image(
            modifier = Modifier.align(CenterVertically),
            painter = if (isChecked) painterResource(R.drawable.ic_checkbox)
                      else painterResource(R.drawable.ic_checkbox_empty),
            contentDescription = stringResource(R.string.label_checkbox))

        GlideImage(
            modifier = Modifier.size(80.dp),
            url = item.thumb)

        Column(modifier = Modifier.weight(1f)) {

        }

        Image(
            modifier = modifier
            .clickable {

            },
            painter = painterResource(R.drawable.ic_close),
            contentDescription = stringResource(R.string.label_close))
    }

    Text(text = (item.price.toMoneyInt() * item.quantity).toMoneyString())

    Divider(modifier = modifier, thickness = 1.dp, color = colorResource(R.color.gray_line))
}

@Composable
private fun CartPriceColumn(
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {

    }
}