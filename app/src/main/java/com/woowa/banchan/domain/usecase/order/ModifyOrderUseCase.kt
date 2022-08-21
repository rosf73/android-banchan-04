package com.woowa.banchan.domain.usecase.order

import com.woowa.banchan.domain.entity.OrderDetailSection.Order
import com.woowa.banchan.domain.exception.NotUpdateException
import com.woowa.banchan.domain.repository.OrderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ModifyOrderUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {

    suspend operator fun invoke(order: Order) = flow {
        emit(orderRepository.modifyOrder(order))
    }.catch {
        emit(Result.failure(NotUpdateException()))
    }.flowOn(Dispatchers.IO)
}