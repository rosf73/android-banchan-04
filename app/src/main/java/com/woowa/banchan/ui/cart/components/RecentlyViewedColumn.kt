package com.woowa.banchan.ui.cart.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.woowa.banchan.R
import com.woowa.banchan.domain.entity.Product
import com.woowa.banchan.ui.recently.testRecentlyList
import com.woowa.banchan.extensions.substringShort

@Composable
fun RecentlyViewedColumn(
    modifier: Modifier = Modifier,
    recentlyList: List<Product>,
    navigateToRecently: () -> Unit
) {
    Column(modifier = modifier.padding(16.dp, 32.dp)) {
        RecentlyViewedHeader(onViewTotalClick = navigateToRecently)

        RecentlyViewedRow(recentlyList)
    }
}

@Composable
private fun RecentlyViewedHeader(
    onViewTotalClick: () -> Unit
) {
    Row(modifier = Modifier.padding(0.dp, 8.dp)) {
        Text(
            modifier = Modifier
                .weight(1f)
                .align(CenterVertically),
            text = stringResource(R.string.cart_recently))

        TextButton(
            onClick = onViewTotalClick,
            colors = ButtonDefaults.textButtonColors(
                contentColor = colorResource(R.color.gray_default)
            )
        ) {
            Text(text = stringResource(R.string.cart_recently_all))
        }
    }
}

@Composable
private fun RecentlyViewedRow(
    recentlyList: List<Product>
) {
    LazyRow(modifier = Modifier) {
        items(recentlyList) { item ->
            RecentlyViewedItem(
                modifier = Modifier
                    .padding(8.dp)
                    .width(120.dp),
                item = item)
        }
    }
}

@Composable
private fun RecentlyViewedItem(
    modifier: Modifier = Modifier,
    item: Product
) {
    Column(modifier = modifier) {
        GlideImage(
            modifier = Modifier.fillMaxWidth(),
            url = item.image)

        Text(text = item.title.substringShort(), fontWeight = FontWeight(500), color = colorResource(R.color.black), maxLines = 1)
        Row {
            Text(text = item.sPrice, fontWeight = FontWeight(500), color = colorResource(R.color.black))
            Text(text = item.nPrice ?: "",
                fontWeight = FontWeight(400), color = colorResource(R.color.gray_default), fontSize = 12.sp,
                style = TextStyle(textDecoration = TextDecoration.LineThrough))
        }
        Text(text = item.viewedAt,
            fontWeight = FontWeight(400), color = colorResource(R.color.gray_default), fontSize = 12.sp)
    }
}

@Preview
@Composable
private fun Product() {
    RecentlyViewedItem(item = testRecentlyList[0])
}