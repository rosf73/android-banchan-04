package com.woowa.banchan.di

import com.woowa.banchan.domain.repository.BanchanRepository
import com.woowa.banchan.domain.usecase.GetDetailProductUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun providesGetDetailProductUseCase(repository: BanchanRepository): GetDetailProductUseCase {
        return GetDetailProductUseCase(repository)
    }
}