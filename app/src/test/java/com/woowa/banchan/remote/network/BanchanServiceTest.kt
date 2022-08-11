package com.woowa.banchan.remote.network

import com.google.common.truth.Truth
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.woowa.banchan.data.remote.dto.CategoryDto
import com.woowa.banchan.data.remote.dto.PlanResponse
import com.woowa.banchan.data.remote.dto.ProductDto
import com.woowa.banchan.data.remote.dto.ProductsResponse
import com.woowa.banchan.data.remote.network.BanchanService
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.io.File


class BanchanServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var banchanService: BanchanService

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        banchanService = Retrofit.Builder()
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder()
                        .add(KotlinJsonAdapterFactory())
                        .build()
                )
            )
            .baseUrl(mockWebServer.url(""))
            .build()
            .create()
    }

    @Test
    fun `기획전의 상품 메뉴를 가져올 수 있다`() = runBlocking {

        // given
        val responseJson = File("src/test/java/com/resources/plan.json").readText()
        val response = MockResponse().setBody(responseJson)
        mockWebServer.enqueue(response)
        val expected = PlanResponse(
            statusCode = 200,
            categories = listOf(
                CategoryDto(
                    categoryId = "17011000",
                    name = "풍성한 고기반찬",
                    products = listOf(
                        ProductDto(
                            detailHash = "HBDEF",
                            image = "http://public.codesquad.kr/jk/storeapp/data/main/1155_ZIP_P_0081_T.jpg",
                            alt = "오리 주물럭_반조리",
                            deliveryType = listOf("새벽배송", "전국택배"),
                            title = "오리 주물럭_반조리",
                            description = "감질맛 나는 매콤한 양념",
                            nPrice = "15,800원",
                            sPrice = "12,640원",
                            badge = listOf("런칭특가")
                        )
                    )
                ),
                CategoryDto(
                    categoryId = "17010200",
                    name = "편리한 반찬세트",
                    products = listOf(
                        ProductDto(
                            detailHash = "HBBCC",
                            image = "http://public.codesquad.kr/jk/storeapp/data/side/48_ZIP_P_5008_T.jpg",
                            alt = "새콤달콤 오징어무침",
                            deliveryType = listOf("새벽배송", "전국택배"),
                            title = "새콤달콤 오징어무침",
                            description = "국내산 오징어를 새콤달콤하게",
                            nPrice = "7,500원",
                            sPrice = "6,000원",
                            badge = listOf("런칭특가")
                        )
                    )
                )
            )
        )

        // when
        val actual = banchanService.getPlan()

        // then
        val actualResult = actual.body()
        Truth.assertThat(actualResult).isEqualTo(expected)
    }

    @Test
    fun `메인요리의 상품 메뉴를 가져올 수 있다`() = runBlocking {

        // given
        val responseJson = File("src/test/java/com/resources/maindish.json").readText()
        val response = MockResponse().setBody(responseJson)
        mockWebServer.enqueue(response)
        val expected = ProductsResponse(
            statusCode = 200,
            products = listOf(
                ProductDto(
                    detailHash = "HBDEF",
                    image = "http://public.codesquad.kr/jk/storeapp/data/main/1155_ZIP_P_0081_T.jpg",
                    alt = "오리 주물럭_반조리",
                    deliveryType = listOf("새벽배송", "전국택배"),
                    title = "오리 주물럭_반조리",
                    description = "감칠맛 나는 매콤한 양념",
                    nPrice = "15,800원",
                    sPrice = "12,640원",
                    badge = listOf("런칭특가")
                ),
                ProductDto(
                    detailHash = "HDF73",
                    image = "http://public.codesquad.kr/jk/storeapp/data/main/310_ZIP_P_0012_T.jpg",
                    alt = "잡채",
                    deliveryType = listOf("새벽배송", "전국택배"),
                    title = "잡채",
                    description = "탱글한 면발과 맛깔진 고명이 가득",
                    nPrice = "12,900원",
                    sPrice = "11,610원",
                    badge = listOf("이벤트특가")
                )
            )
        )

        // when
        val actual = banchanService.getMainDish()

        // then
        val actualResult = actual.body()
        Truth.assertThat(actualResult).isEqualTo(expected)
    }

    @Test
    fun `국물요리의 상품 메뉴를 가져올 수 있다`() = runBlocking {

        // given
        val responseJson = File("src/test/java/com/resources/soup.json").readText()
        val response = MockResponse().setBody(responseJson)
        mockWebServer.enqueue(response)
        val expected = ProductsResponse(
            statusCode = 200,
            products = listOf(
                ProductDto(
                    detailHash = "H72C3",
                    image = "http://public.codesquad.kr/jk/storeapp/data/soup/28_ZIP_P_1003_T.jpg",
                    alt = "한돈 돼지 김치찌개",
                    deliveryType = listOf("새벽배송", "전국택배"),
                    title = "한돈 돼지 김치찌개",
                    description = "김치찌개에는 역시 돼지고기",
                    nPrice = "9,300원",
                    sPrice = "8,370원",
                    badge = listOf("이벤트특가")
                ),
                ProductDto(
                    detailHash = "HA6EE",
                    image = "http://public.codesquad.kr/jk/storeapp/data/soup/33_ZIP_P_1004_T.jpg",
                    alt = "된장찌개",
                    deliveryType = listOf("새벽배송", "전국택배"),
                    title = "된장찌개",
                    description = "특별하지 않아서 더 좋은 우리맛",
                    nPrice = "8,800원",
                    sPrice = "7,920원",
                    badge = listOf("이벤트특가")
                )
            )
        )

        // when
        val actual = banchanService.getSoup()

        // then
        val actualResult = actual.body()
        Truth.assertThat(actualResult).isEqualTo(expected)
    }
}