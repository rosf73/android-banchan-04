package com.woowa.banchan.data.local.cart

import com.google.common.truth.Truth
import com.woowa.banchan.data.local.entity.CartEntity
import com.woowa.banchan.data.local.entity.toCart
import com.woowa.banchan.data.local.repository.CartRepositoryImpl
import com.woowa.banchan.domain.entity.Cart
import com.woowa.banchan.domain.repository.CartRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CartRepositoryTest {

    private lateinit var fakeEntityList: List<CartEntity>
    private lateinit var fakeDataSource: FakeCartDataSource
    private lateinit var repository: CartRepository

    @Before
    fun setUp() {
        fakeEntityList = listOf(
            CartEntity(
                id = 1L,
                hash = "HASH19",
                name = "소고기",
                imageUrl = "A",
                quantity = 3,
                price = "3,000원",
                check = true
            ),
            CartEntity(
                id = 2L,
                hash = "HASH31",
                name = "돼지고기",
                imageUrl = "B",
                quantity = 1,
                price = "1,500원",
                check = false
            )
        )

        fakeDataSource = FakeCartDataSource(fakeEntityList)
        repository = CartRepositoryImpl(fakeDataSource)
    }

    @Test
    fun `장바구니가_정상_조회된다`() = runTest {
        // when
        val actual = repository.getCarts().firstOrNull()?.values?.toList()

        // then
        Truth.assertThat(actual)
            .isEqualTo(fakeEntityList.map { it.toCart() })
    }

    @Test
    fun `장바구니의_체크여부와_수량이_정상_수정된다`() = runTest {
        val newList = fakeEntityList.map { // 기존 데이터를 모두 수량 + 1, 체크 반대
            it.copy(
                check = !it.check,
                quantity = it.quantity + 1
            ).toCart()
        }

        repository.updateCart(*newList.toTypedArray())

        // when
        val actual = repository.getCarts().firstOrNull()?.values?.toList()

        // then
        Truth.assertThat(actual)
            .isEqualTo(newList)
    }

    @Test
    fun `장바구니가_정상_추가된다`() = runTest {
        val newData = Cart(
            id = 3L,
            hash = "HASH6",
            name = "닭고기",
            imageUrl = "C",
            quantity = 41,
            price = "82,000원",
            checked = false
        )

        repository.addCart(newData)

        // when
        val actual = repository.getCarts().firstOrNull()?.values?.toList()

        // then
        Truth.assertThat(actual)
            .contains(newData)
    }
}
