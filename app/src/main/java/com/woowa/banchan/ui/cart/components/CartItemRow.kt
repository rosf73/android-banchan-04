package com.woowa.banchan.ui.cart.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.woowa.banchan.R
import com.woowa.banchan.ui.cart.TestCartItem
import com.woowa.banchan.utils.toMoneyInt
import com.woowa.banchan.utils.toMoneyString

@Composable
fun CartItemRow(
    modifier: Modifier = Modifier,
    item: TestCartItem,
    onCheck: () -> Unit,
    onUncheck: () -> Unit
) {
    val (isChecked, setIsChecked) = remember { mutableStateOf(true) }

    Column(modifier = modifier) {
        Row(modifier = Modifier
            .clickable {
                if (isChecked) {
                    onUncheck()
                    setIsChecked(false)
                } else {
                    onCheck()
                    setIsChecked(true)
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
                url = item.thumb
            )

            Column(
                modifier = Modifier.weight(1f, true)
            ) {
                Text(text = item.name)
                Text(text = item.price)
            }

            Image(
                modifier = Modifier
                    .clickable {

                    },
                painter = painterResource(R.drawable.ic_close),
                contentDescription = stringResource(R.string.label_close)
            )
        }

        Text(
            modifier = Modifier.align(Alignment.End).padding(16.dp),
            text = (item.price.toMoneyInt() * item.quantity).toMoneyString()
        )

        Divider(modifier = modifier, thickness = 1.dp, color = colorResource(R.color.gray_line))
    }
}

@Composable
private fun CartItemQuantityRow() {

}