package com.woowa.banchan.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.woowa.banchan.data.local.dao.CartDao
import com.woowa.banchan.data.local.database.AppDatabase
import com.woowa.banchan.data.local.entity.CartEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class CartDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var dao: CartDao
    private val cart = CartEntity(
        1L,
        "새콤달콤 오징어무침",
        "http://public.codesquad.kr/jk/storeapp/data/side/48_ZIP_P_5008_T.jpg",
        3,
        "6,000원",
        false
    )

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.cartDao()
    }

    @Test
    fun `장바구니에_상품을_넣을_수_있다`() = runTest {
        dao.insertCart(cart)
        val cartList = dao.findAll().first()

        assertThat(cartList).contains(cart)
    }

    @Test
    fun `장바구니에_상품을_여러개_넣을_수_있다`() = runTest {
        var insertList = arrayOf(
            cart, CartEntity(
                2L,
                "무진장 매운 오이무침",
                "http://public.codesquad.kr/jk/storeapp/data/side/48_ZIP_P_5008_T.jpg",
                2,
                "6,000원",
                false
            )
        )
        dao.insertCart(*insertList)
        val cartList = dao.findAll().first()

        assertThat(cartList).hasSize(2)
    }

    @Test
    fun `장바구니에서_데이터를_삭제할_수_있다`() = runTest {
        dao.insertCart(cart)
        dao.deleteCart(cart)

        val cartList = dao.findAll().first()
        assertThat(cartList).isEmpty()
    }

    @Test
    fun `장바구니에서_데이터를_여러개_삭제할_수_있다`() = runTest {
        var insertList = arrayOf(
            cart, CartEntity(
                2L,
                "무진장 매운 오이무침",
                "http://public.codesquad.kr/jk/storeapp/data/side/48_ZIP_P_5008_T.jpg",
                2,
                "6,000원",
                false
            )
        )
        dao.insertCart(*insertList)
        dao.deleteCart(*insertList)
        val cartList = dao.findAll().first()

        assertThat(cartList).isEmpty()
    }

    @Test
    fun `장바구니에_데이터가_존재하는지_확인할_수_있다`() = runTest {
        dao.insertCart(cart)
        val result = dao.existById(cart.id).first()
        assertThat(result).isTrue()
    }

    @Test
    fun `장바구니에_데이터의_수량과_선택을_수정할_수_있다`() = runTest {
        dao.insertCart(cart)
        val updateCart = cart.copy(quantity = 1, check = false)
        dao.insertCart(updateCart)

        val result = dao.findAll().first()
        assertThat(result).isEqualTo(listOf(updateCart))
    }

    @After
    fun teardown() {
        database.close()
    }
}