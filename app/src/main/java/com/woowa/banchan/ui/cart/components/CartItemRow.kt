package com.woowa.banchan.ui.cart.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.woowa.banchan.R
import com.woowa.banchan.domain.entity.Cart
import com.woowa.banchan.extensions.toMoneyInt
import com.woowa.banchan.extensions.toMoneyString

@Composable
fun CartItemRow(
    modifier: Modifier = Modifier,
    item: Cart,
    onCheck: () -> Unit,
    onUncheck: () -> Unit,
    onDeleteClick: () -> Unit,
    onQuantityChanged: (Int, Boolean) -> Unit
) {
    var isChecked by remember { mutableStateOf(item.checked) }
    var quantity by remember { mutableStateOf(item.quantity) }
    quantity = item.quantity
    isChecked = item.checked

    Column(modifier = modifier) {
        Row(modifier = Modifier
            .clickable {
                isChecked = if (isChecked) {
                    onUncheck()
                    false
                } else {
                    onCheck()
                    true
                }
            }
            .padding(20.dp, 20.dp, 20.dp, 0.dp)
        ) {
            Image(
                modifier = Modifier.align(Alignment.CenterVertically),
                painter = if (isChecked) painterResource(R.drawable.ic_checkbox)
                else painterResource(R.drawable.ic_checkbox_empty),
                contentDescription = stringResource(R.string.label_checkbox)
            )

            GlideImage(
                modifier = Modifier
                    .padding(20.dp, 0.dp, 16.dp, 0.dp)
                    .size(80.dp),
                url = item.imageUrl
            )

            Column(
                modifier = Modifier.weight(1f, true)
            ) {
                Text(text = item.name)
                Text(text = item.price)
                CartItemQuantityRow(
                    initQuantity = quantity,
                    onQuantityChanged = { q, type ->
                        onQuantityChanged(q, type)
                        quantity = q
                    })
            }

            Image(
                modifier = Modifier
                    .clickable { onDeleteClick() },
                painter = painterResource(R.drawable.ic_close),
                contentDescription = stringResource(R.string.label_close)
            )
        }

        Text(
            modifier = Modifier
                .align(Alignment.End)
                .padding(16.dp),
            text = (item.price.toMoneyInt() * quantity).toMoneyString()
        )

        Divider(modifier = modifier, thickness = 1.dp, color = colorResource(R.color.gray_line))
    }
}

@Composable
private fun CartItemQuantityRow(
    initQuantity: Int = 1,
    onQuantityChanged: (Int, Boolean) -> Unit
) {
    var quantity by remember { mutableStateOf(initQuantity) }
    quantity = initQuantity

    Row {
        Surface(modifier = Modifier.size(24.dp), shape = CircleShape, elevation = 6.dp) {
            Box(
                modifier = Modifier
                    .clickable {
                        if (quantity > 1) {
                            onQuantityChanged(quantity - 1, false)
                            quantity--
                        }
                    }
            ) {
                Image(
                    modifier = Modifier.align(Alignment.Center),
                    painter = painterResource(R.drawable.ic_minus_mini),
                    contentDescription = stringResource(R.string.label_minus)
                )
            }
        }
        BasicTextField(
            modifier = Modifier.width(32.dp),
            value = quantity.toString(),
            onValueChange = { text ->
                quantity = text.toInt()
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
        )
        Surface(modifier = Modifier.size(24.dp), shape = CircleShape, elevation = 4.dp) {
            Box(
                modifier = Modifier
                    .clickable {
                        onQuantityChanged(quantity + 1, true)
                        quantity++
                    }
            ) {
                Image(
                    modifier = Modifier.align(Alignment.Center),
                    painter = painterResource(R.drawable.ic_plus_mini),
                    contentDescription = stringResource(R.string.label_plus)
                )
            }
        }
    }
}