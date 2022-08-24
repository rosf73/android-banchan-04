package com.woowa.banchan.ui.screen.cart.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
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
    var quantity by remember { mutableStateOf(item.quantity) }

    LaunchedEffect(item.id, item.quantity) {
        quantity = item.quantity
    }

    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .clickable {
                    if (item.checked) {
                        onUncheck()
                    } else {
                        onCheck()
                    }
                }
                .padding(20.dp, 20.dp, 20.dp, 0.dp)
        ) {
            Image(
                modifier = Modifier.align(Alignment.CenterVertically),
                painter = if (item.checked) painterResource(R.drawable.ic_checkbox)
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
                    quantity = quantity,
                    onQuantityChanged = { q, type ->
                        onQuantityChanged(q, type)
                        quantity = q
                    }
                )
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
    quantity: Int,
    onQuantityChanged: (Int, Boolean) -> Unit
) {
    Row {
        CartItemQuantityButton(
            painter = painterResource(R.drawable.ic_minus_mini),
            contentDescription =  stringResource(R.string.label_minus),
            onQuantityChanged = {
                if (quantity > 1) {
                    onQuantityChanged(quantity-1, false)
                }
            }
        )

        CartItemQuantityTextField(
            text = TextFieldValue(
                text = quantity.toString(),
                selection = TextRange(quantity.toString().length)
            ),
            onTextChanged = {
                val newQuantity = it.text.toInt()
                onQuantityChanged(newQuantity, newQuantity >= quantity)
            }
        )

        CartItemQuantityButton(
            painter = painterResource(R.drawable.ic_plus_mini),
            contentDescription = stringResource(R.string.label_plus),
            onQuantityChanged = {
                onQuantityChanged(quantity+1, true)
            }
        )
    }
}

@Composable
private fun CartItemQuantityButton(
    painter: Painter,
    contentDescription: String,
    onQuantityChanged: () -> Unit,
) {
    Surface(modifier = Modifier.size(24.dp), shape = CircleShape, elevation = 4.dp) {
        Box(
            modifier = Modifier
                .clickable { onQuantityChanged() }
        ) {
            Image(
                modifier = Modifier.align(Alignment.Center),
                painter = painter,
                contentDescription = contentDescription
            )
        }
    }
}

@Composable
private fun CartItemQuantityTextField(
    text: TextFieldValue,
    onTextChanged: (TextFieldValue) -> Unit
) {
    BasicTextField(
        modifier = Modifier.width(32.dp),
        value = text,
        onValueChange = {
            if (it.text.isEmpty())
                onTextChanged(TextFieldValue("1", selection = TextRange(1)))
            else if (it.text.length < 12) {
                val newQuantity = it.text
                onTextChanged(
                    TextFieldValue(
                        newQuantity,
                        selection = TextRange(newQuantity.length)
                    )
                )
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
    )
}
