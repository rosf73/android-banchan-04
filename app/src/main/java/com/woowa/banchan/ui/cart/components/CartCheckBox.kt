package com.woowa.banchan.ui.cart.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.woowa.banchan.R

@Composable
fun CartCheckBox(
    modifier: Modifier = Modifier,
    onCheck: () -> Unit,
    onUncheck: () -> Unit,
    onDeleteClick: () -> Unit
) {
    val (isChecked, setIsChecked) = remember { mutableStateOf(true) }

    Row(modifier = modifier) {
        Image(
            modifier = Modifier
                .clickable {
                    if (isChecked) {
                        onUncheck()
                        setIsChecked(false)
                    } else {
                        onCheck()
                        setIsChecked(true)
                    }
                }
                .padding(20.dp),
            painter = if (isChecked) painterResource(R.drawable.ic_checkbox)
                      else painterResource(R.drawable.ic_checkbox_empty),
            contentDescription = stringResource(R.string.label_checkbox))

        Text(
            modifier = Modifier
                .weight(1f)
                .align(CenterVertically),
            text = if (isChecked) stringResource(R.string.cart_deselect)
                   else stringResource(R.string.cart_select_all),
            color = colorResource(R.color.black))

        TextButton(
            modifier = Modifier
                .align(CenterVertically)
                .padding(20.dp, 0.dp),
            onClick = onDeleteClick,
            colors = ButtonDefaults.textButtonColors(
                contentColor = colorResource(R.color.gray_default)
            )
        ) {
            Text(text = stringResource(R.string.cart_delete))
        }
    }

    Divider(modifier = modifier, thickness = 1.dp, color = colorResource(R.color.gray_line))
}