package com.woowa.banchan.domain.usecase

import com.woowa.banchan.domain.entity.DetailProduct
import com.woowa.banchan.domain.repository.BanchanRepository
import kotlinx.coroutines.flow.Flow

class GetDetailProductUseCase(
    private val banchanRepository: BanchanRepository
) {

    operator fun invoke(hash: String): Flow<Result<DetailProduct>> {
        return banchanRepository.getDetailProduct(hash)
    }
}