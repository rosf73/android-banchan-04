package com.woowa.banchan.di

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
}