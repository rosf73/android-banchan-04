package com.woowa.banchan.domain.usecase.order

import com.woowa.banchan.domain.exception.NotFoundProductsException
import com.woowa.banchan.domain.repository.OrderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetOrderLineItemUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {

    operator fun invoke(orderId: Long) = flow {
        orderRepository.getOrderLineItem(orderId).collect { emit(it) }
    }.catch {
        emit(Result.failure(NotFoundProductsException()))
    }.flowOn(Dispatchers.IO)
}