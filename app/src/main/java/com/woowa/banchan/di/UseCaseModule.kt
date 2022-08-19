package com.woowa.banchan.di

import com.woowa.banchan.domain.repository.BanchanRepository
import com.woowa.banchan.domain.repository.CartRepository
import com.woowa.banchan.domain.usecase.*
import com.woowa.banchan.domain.repository.RecentlyViewedRepository
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
    fun providesAddCartUseCase(
        repository: CartRepository,
        existCartUseCase: ExistCartUseCase,
        modifyCartUseCase: ModifyCartUseCase
    ): AddCartUseCase {
        return AddCartUseCase(repository, existCartUseCase, modifyCartUseCase)
    }

    @Provides
    @Singleton
    fun providesGetCartUseCase(repository: CartRepository): GetCartUseCase {
        return GetCartUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesExistCartUseCase(repository: CartRepository): ExistCartUseCase {
        return ExistCartUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesModifyCartUseCase(repository: CartRepository): ModifyCartUseCase {
        return ModifyCartUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesGetAllRecentlyViewedUseCase(repository: RecentlyViewedRepository): GetAllRecentlyViewedUseCase {
        return GetAllRecentlyViewedUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesModifyRecentlyViewedUseCase(repository: RecentlyViewedRepository): ModifyRecentlyViewedUseCase {
        return ModifyRecentlyViewedUseCase(repository)
    }
}