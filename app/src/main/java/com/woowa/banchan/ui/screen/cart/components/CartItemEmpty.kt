package com.woowa.banchan.ui.screen.cart.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.woowa.banchan.R

@Composable
fun CartItemEmpty(
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
