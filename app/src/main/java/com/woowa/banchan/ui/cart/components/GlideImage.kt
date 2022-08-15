package com.woowa.banchan.ui.cart.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.woowa.banchan.R

@Composable
fun GlideImage(
    modifier: Modifier,
    url: String
) {
    com.skydoves.landscapist.glide.GlideImage(
        modifier = modifier,
        imageModel = url,
        contentScale = ContentScale.FillWidth,
        loading = {
            Box(
                modifier = Modifier.background(colorResource(R.color.grey_surface))
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_logo),
                    contentDescription = stringResource(R.string.label_logo)
                )
            }
        },
        failure = {
            Text(modifier = Modifier.align(Alignment.Center), text = "이미지를 로드할 수 없습니다.")
        })
}