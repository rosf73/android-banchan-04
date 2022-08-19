package com.woowa.banchan.di

import com.woowa.banchan.domain.repository.BanchanRepository
import com.woowa.banchan.domain.repository.RecentlyViewedRepository
import com.woowa.banchan.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetPlanUseCase(banchanRepository: BanchanRepository): GetPlanUseCase {
        return GetPlanUseCase(banchanRepository)
    }

    @Provides
    @Singleton
    fun providesGetDetailProductUseCase(repository: BanchanRepository): GetDetailProductUseCase {
        return GetDetailProductUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesGetProductsUseCase(repository: BanchanRepository): GetProductsUseCase {
        return GetProductsUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesGetAllRecentlyViewedUseCase(repository: RecentlyViewedRepository): GetAllRecentlyViewedUseCase {
        return GetAllRecentlyViewedUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesAddRecentlyViewedUseCase(repository: RecentlyViewedRepository): AddRecentlyViewedUseCase {
        return AddRecentlyViewedUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesModifyRecentlyViewedUseCase(repository: RecentlyViewedRepository): ModifyRecentlyViewedUseCase {
        return ModifyRecentlyViewedUseCase(repository)
    }
}