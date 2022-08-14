package com.woowa.banchan.ui.cart

import com.woowa.banchan.utils.toMoneyString

data class TestCartItem(
    val thumb: String,
    val name: String,
    val price: String,
    val quantity: Int
)

val testCartItem = listOf(
    TestCartItem(
        thumb = "http://public.codesquad.kr/jk/storeapp/data/side/48_ZIP_P_5008_T.jpg",
        name = "한돈 매콤 안심 장조림",
        price = "6,210원",
        quantity = 2
    ),
    TestCartItem(
        thumb = "http://public.codesquad.kr/jk/storeapp/data/soup/213_ZIP_P_1008_T.jpg",
        name = "미역오이냉국",
        price = "7,020원",
        quantity = 1
    ),
    TestCartItem(
        thumb = "http://public.codesquad.kr/jk/storeapp/data/main/675_ZIP_P_0057_T.jpg",
        name = "궁중 떡볶이_반조리",
        price = "7,800원",
        quantity = 3
    ),
    TestCartItem(
        thumb = "http://public.codesquad.kr/jk/storeapp/data/main/349_ZIP_P_0024_T.jpg",
        name = "소갈비찜",
        price = "26,010원",
        quantity = 1
    )
)