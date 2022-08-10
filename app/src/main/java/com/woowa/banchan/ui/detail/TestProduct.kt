package com.woowa.banchan.ui.detail

var quantity = 1

data class TestProduct(
    val name: String,
    val thumbs: List<String>,
    val sPrice: String,
    val nPrice: String?,
    val point: String,
    val deliveryInfo: String,
    val deliveryFee: String,
    val section: List<String>
) {

    val discountRate: Int
        get() = if (nPrice == null) 0
        else {
            val tempS = sPrice.replace(Regex(",|원"), "").toFloat()
            val tempN = nPrice.replace(Regex(",|원"), "").toFloat()
            ((tempN - tempS) / tempN * 100).toInt()
        }
}

val testProduct = TestProduct(
    name = "오리 주물럭_반조리",
    thumbs = listOf(
        "http://public.codesquad.kr/jk/storeapp/data/main/1155_ZIP_P_0081_T.jpg",
        "http://public.codesquad.kr/jk/storeapp/data/main/1155_ZIP_P_0081_S.jpg"
    ),
    sPrice = "12,640원",
    nPrice = "15,800원",
    point = "126원",
    deliveryInfo = "서울 경기 새벽 배송, 전국 택배 배송",
    deliveryFee = "2,500원 (40,000원 이상 구매 시 무료)",
    section = listOf(
        "http://public.codesquad.kr/jk/storeapp/data/main/510_ZIP_P_0047_D1.jpg",
        "http://public.codesquad.kr/jk/storeapp/data/main/510_ZIP_P_0047_D2.jpg",
        "http://public.codesquad.kr/jk/storeapp/data/main/510_ZIP_P_0047_D3.jpg",
        "http://public.codesquad.kr/jk/storeapp/data/pakage_regular.jpg"
    )
)