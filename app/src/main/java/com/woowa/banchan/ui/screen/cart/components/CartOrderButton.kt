package com.woowa.banchan.ui.screen.cart.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.woowa.banchan.R
import com.woowa.banchan.extensions.toMoneyString

@Composable
fun CartOrderButton(
    totalPrice: Int,
    onOrderClick: () -> Unit
) {
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
        Text(
            text = "${orderPrice.toMoneyString()} 주문하기",
            color = colorResource(R.color.white)
        )
    }

    if (totalPrice < 40000)
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(align = CenterHorizontally),
            text = "${(40000 - totalPrice).toMoneyString()}을 더 담으면 배송비가 무료!"
        )
}
