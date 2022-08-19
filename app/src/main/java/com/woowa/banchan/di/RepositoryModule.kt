package com.woowa.banchan.di

import com.woowa.banchan.data.local.datasource.*
import com.woowa.banchan.data.local.repository.*
import com.woowa.banchan.data.remote.datasource.BanchanDataSource
import com.woowa.banchan.data.remote.repository.BanchanRepositoryImpl
import com.woowa.banchan.domain.repository.*
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
    fun provideOrderRepository(orderDataSource: OrderDataSource): OrderRepository {
        return OrderRepositoryImpl(orderDataSource)
    }

    @Provides
    @Singleton
    fun provideRecentlyViewedRepository(recentlyViewedDataSource: RecentlyViewedDataSource): RecentlyViewedRepository {
        return RecentlyViewedRepositoryImpl(recentlyViewedDataSource)
    }
}