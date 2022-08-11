package com.woowa.banchan.data.remote.repository

import com.google.common.truth.Truth
import com.woowa.banchan.data.remote.datasource.BanchanDataSource
import com.woowa.banchan.data.remote.dto.CategoryDto
import com.woowa.banchan.data.remote.dto.PlanResponse
import com.woowa.banchan.data.remote.dto.ProductDto
import com.woowa.banchan.data.remote.dto.toCategory
import com.woowa.banchan.data.remote.network.BanchanService
import com.woowa.banchan.domain.entity.Category
import com.woowa.banchan.domain.repository.BanchanRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class BanchanRepositoryImplTest {

    private lateinit var banchanService: BanchanService
    private lateinit var banchanDataSource: BanchanDataSource
    private lateinit var banchanRepository: BanchanRepository

    @Before
    fun setUp() {
        banchanService = mockk(relaxed = true)
        banchanDataSource = mockk(relaxed = true)
        banchanRepository = BanchanRepositoryImpl(banchanDataSource)
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

        // when, then
        var actualCategory: List<Category>?
        banchanRepository.getPlan().collect {
            actualCategory = it.getOrNull()
            Truth.assertThat(actualCategory)
                .isEqualTo(expectedResponse.categories.map { it.toCategory() })
        }
    }

    @Test
    fun getProducts() {
    }

    @Test
    fun getDetailProduct() {
    }
}