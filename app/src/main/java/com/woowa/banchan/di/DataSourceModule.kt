package com.woowa.banchan.di

import com.woowa.banchan.data.local.dao.*
import com.woowa.banchan.data.local.datasource.*
import com.woowa.banchan.data.remote.datasource.*
import com.woowa.banchan.data.remote.network.BanchanService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataSourceModule {

    @Provides
    @Singleton
    fun provideBanchanDataSource(banchanService: BanchanService): BanchanDataSource {
        return BanchanRemoteDataSource(banchanService)
    }

    @Provides
    @Singleton
    fun provideCartDataSource(cartDao: CartDao): CartDataSource {
        return CartLocalDataSource(cartDao)
    }

    @Provides
    @Singleton
    fun provideOrderDataSource(orderDao: OrderDao): OrderDataSource {
        return OrderDataSourceImpl(orderDao)
    }

    @Provides
    @Singleton
    fun provideRecentlyViewedDataSource(recentlyViewedDao: RecentlyViewedDao): RecentlyViewedDataSource {
        return RecentlyViewedDataSourceImpl(recentlyViewedDao)
    }
}