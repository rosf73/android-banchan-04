package com.woowa.banchan.di

import com.woowa.banchan.data.local.datasource.CartDataSource
import com.woowa.banchan.data.local.datasource.OrderDataSource
import com.woowa.banchan.data.local.datasource.RecentlyViewedDataSource
import com.woowa.banchan.data.local.repository.CartRepositoryImpl
import com.woowa.banchan.data.local.repository.OrderRepositoryImpl
import com.woowa.banchan.data.local.repository.RecentlyViewedRepositoryImpl
import com.woowa.banchan.data.remote.datasource.BanchanDataSource
import com.woowa.banchan.data.remote.repository.BanchanRepositoryImpl
import com.woowa.banchan.domain.repository.BanchanRepository
import com.woowa.banchan.domain.repository.CartRepository
import com.woowa.banchan.domain.repository.OrderRepository
import com.woowa.banchan.domain.repository.RecentlyViewedRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun provideBanchanRepository(banchanDataSource: BanchanDataSource): BanchanRepository {
        return BanchanRepositoryImpl(banchanDataSource)
    }

    @Provides
    @Singleton
    fun provideCartRepository(cartDataSource: CartDataSource): CartRepository {
        return CartRepositoryImpl(cartDataSource)
    }

    @Provides
    @Singleton
    fun provideOrderRepository(orderDataSource: OrderDataSource): OrderRepository {
        return OrderRepositoryImpl(orderDataSource)
    }

    @Provides
    @Singleton
    fun provideRecentlyViewedRepository(recentlyViewedDataSource: RecentlyViewedDataSource): RecentlyViewedRepository {
        return RecentlyViewedRepositoryImpl(recentlyViewedDataSource)
    }
}
