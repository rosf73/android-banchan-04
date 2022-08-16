package com.woowa.banchan.ui.recently

data class TestRecently(
    val id: Long,
    val thumb: String,
    val name: String,
    val sPrice: String,
    val nPrice: String,
    val viewedAt: String
)

val testRecentlyList = listOf(
    TestRecently(
        id = 0L,
        thumb = "http://public.codesquad.kr/jk/storeapp/data/main/349_ZIP_P_0024_T.jpg",
        name = "소갈비찜",
        sPrice = "28,900원",
        nPrice = "26,010원",
        viewedAt = "1분 전"
    ),
    TestRecently(
        id = 0L,
        thumb = "http://public.codesquad.kr/jk/storeapp/data/main/510_ZIP_P_0047_T.jpg",
        name = "테스트테스트테스트테스트",
        sPrice = "28,900원",
        nPrice = "26,010원",
        viewedAt = "10분 전"
    ),
    TestRecently(
        id = 0L,
        thumb = "http://public.codesquad.kr/jk/storeapp/data/main/349_ZIP_P_0024_T.jpg",
        name = "테스트2",
        sPrice = "28,900원",
        nPrice = "26,010원",
        viewedAt = "40분 전"
    ),
    TestRecently(
        id = 0L,
        thumb = "http://public.codesquad.kr/jk/storeapp/data/main/510_ZIP_P_0047_T.jpg",
        name = "테스트3",
        sPrice = "28,900원",
        nPrice = "26,010원",
        viewedAt = "42분 전"
    ),
    TestRecently(
        id = 0L,
        thumb = "http://public.codesquad.kr/jk/storeapp/data/main/349_ZIP_P_0024_T.jpg",
        name = "테스트4",
        sPrice = "28,900원",
        nPrice = "26,010원",
        viewedAt = "58분 전"
    )
)