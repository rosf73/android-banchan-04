package com.woowa.banchan.data.local

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth
import com.woowa.banchan.data.local.entity.RecentlyViewedEntity
import com.woowa.banchan.data.local.entity.toRecentlyViewed
import com.woowa.banchan.data.local.repository.RecentlyViewedRepositoryImpl
import com.woowa.banchan.domain.entity.RecentlyViewed
import com.woowa.banchan.domain.repository.RecentlyViewedRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class RecentlyViewedRepositoryTest {

    private lateinit var fakeEntityList: List<RecentlyViewedEntity>
    private lateinit var fakeDataSource: FakeRecentlyViewedDataSource
    private lateinit var repository: RecentlyViewedRepository

    @Before
    fun setUp() {
        fakeEntityList = listOf(
            RecentlyViewedEntity(
                id = 1L,
                hash = "H",
                imageUrl = "http://test",
                name = "소고기",
                description = "",
                nPrice = "0원",
                sPrice = "0원",
                viewedAt = 100L
            )
        )
        fakeDataSource = FakeRecentlyViewedDataSource(recentlyViewedList = fakeEntityList)
        repository = RecentlyViewedRepositoryImpl(fakeDataSource)
    }

    @Test
    fun `최근_본_상품이_정상_조회된다`() = runTest {
        // when
        val result = repository.getAllRecentlyViewed().firstOrNull()
        val actual = result?.getOrNull()

        // then
        Truth.assertThat(actual)
            .isEqualTo(fakeEntityList.map { it.toRecentlyViewed() })
    }
}