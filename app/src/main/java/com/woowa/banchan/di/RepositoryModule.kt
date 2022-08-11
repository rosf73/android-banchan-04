package com.woowa.banchan.di

import com.woowa.banchan.data.remote.datasource.BanchanDataSource
import com.woowa.banchan.data.remote.repository.BanchanRepositoryImpl
import com.woowa.banchan.domain.repository.BanchanRepository
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
}