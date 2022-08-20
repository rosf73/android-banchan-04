package com.woowa.banchan.di

import com.woowa.banchan.data.local.dao.CartDao
import com.woowa.banchan.data.local.dao.RecentlyViewedDao
import com.woowa.banchan.data.local.datasource.CartDataSource
import com.woowa.banchan.data.local.datasource.CartLocalDataSource
import com.woowa.banchan.data.local.datasource.RecentlyViewedDataSource
import com.woowa.banchan.data.local.datasource.RecentlyViewedDataSourceImpl
import com.woowa.banchan.data.remote.datasource.BanchanDataSource
import com.woowa.banchan.data.remote.datasource.BanchanRemoteDataSource
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
    fun provideRecentlyViewedDataSource(recentlyViewedDao: RecentlyViewedDao): RecentlyViewedDataSource {
        return RecentlyViewedDataSourceImpl(recentlyViewedDao)
    }
}