package com.woowa.banchan.data.remote.repository

import com.google.common.truth.Truth.assertThat
import com.woowa.banchan.data.remote.datasource.BanchanDataSource
import com.woowa.banchan.data.remote.dto.*
import com.woowa.banchan.data.remote.network.BanchanService
import com.woowa.banchan.domain.repository.BanchanRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class BanchanRepositoryImplTest {

    private lateinit var banchanService: BanchanService
    private lateinit var banchanDataSource: BanchanDataSource
    private lateinit var banchanRepository: BanchanRepository

    private lateinit var fakeProductResponse: ProductsResponse
    private lateinit var fakeDataSource: FakeDataSource
    private lateinit var banchanRepositoryForFakeTest: BanchanRepository

    @Before
    fun setUp() {
        // mock Test Double
        banchanService = mockk(relaxed = true)
        banchanDataSource = mockk(relaxed = true)
        banchanRepository = BanchanRepositoryImpl(banchanDataSource)

        // fake Test Double
        fakeProductResponse = ProductsResponse(
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
        fakeDataSource = FakeDataSource(products = fakeProductResponse)
        banchanRepositoryForFakeTest = BanchanRepositoryImpl(fakeDataSource)
    }

    @Test
    fun `기획전의 상품 메뉴를 가져온다`() = runBlocking {
        // given
        val expectedResponse = PlanResponse(
            statusCode = 200,
            categories = listOf(
                CategoryDto(
                    categoryId = "ABC",
                    products = listOf(
                        ProductDto(
                            alt = "1",
                            badge = null,
                            deliveryType = listOf("2", "3"),
                            description = "woowa",
                            detailHash = "ABCD",
                            image = "https://www.abcd.com",
                            nPrice = "1,200d원",
                            sPrice = "500원",
                            title = "man"
                        )
                    ),
                    name = "풍부한 고기덩어리"
                )
            )
        )
        val banchanCategory = Result.success(expectedResponse)
        coEvery { banchanDataSource.getPlan() } returns banchanCategory

        // when
        val actualCategory = banchanRepository.getPlan().first().getOrNull()

        // then
        assertThat(actualCategory)
            .isEqualTo(expectedResponse.categories.map { it.toCategory() })
    }

    @Test
    fun `메인요리의 상품 메뉴를 가져온다`() = runBlocking {
        // when
        val products = banchanRepositoryForFakeTest.getProducts("main").first().getOrNull()

        // then
        assertThat(products)
            .isEqualTo(fakeProductResponse.products.map { it.toProduct() })
    }
}