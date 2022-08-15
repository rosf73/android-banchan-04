package com.woowa.banchan.di

import com.woowa.banchan.domain.repository.BanchanRepository
import com.woowa.banchan.domain.usecase.GetDetailProductUseCase
import com.woowa.banchan.domain.usecase.GetPlanUseCase
import com.woowa.banchan.domain.usecase.GetProductsUseCase
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
}