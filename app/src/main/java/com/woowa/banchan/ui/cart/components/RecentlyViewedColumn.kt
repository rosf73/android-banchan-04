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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.woowa.banchan.R

@Composable
fun RecentlyViewedColumn(
    modifier: Modifier = Modifier,
    recentlyList: List<TestRecently>
) {
    Column(modifier = modifier) {
        RecentlyViewedHeader(onViewTotalClick = {})

        RecentlyViewedRow(recentlyList)
    }
}

@Composable
private fun RecentlyViewedHeader(
    onViewTotalClick: () -> Unit
) {
    Row(modifier = Modifier.padding(20.dp)) {
        Text(
            modifier = Modifier
                .weight(1f)
                .align(CenterVertically),
            text = stringResource(R.string.cart_recently))

        TextButton(
            onClick = { /*TODO*/ },
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
    recentlyList: List<TestRecently>
) {
    LazyRow {
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
    item: TestRecently
) {
    Column(modifier = modifier) {
        GlideImage(
            modifier = Modifier.size(120.dp),
            url = item.thumb)

        Text(text = item.name)
        Text(text = item.sPrice)
        Text(text = item.nPrice)
        Text(text = item.viewedAt)
    }
}

@Preview
@Composable
private fun TestRecently() {
    RecentlyViewedItem(item = testRecentlyList[0])
}