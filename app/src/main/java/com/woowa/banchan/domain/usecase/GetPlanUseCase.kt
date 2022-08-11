package com.woowa.banchan.domain.usecase

import com.woowa.banchan.domain.entity.Category
import com.woowa.banchan.domain.repository.BanchanRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPlanUseCase @Inject constructor(
    private val repository: BanchanRepository
) {
    operator fun invoke(): Flow<Result<List<Category>>> {
        return repository.getPlan()
    }
}