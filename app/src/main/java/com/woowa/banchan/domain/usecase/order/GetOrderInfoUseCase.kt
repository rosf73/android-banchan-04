package com.woowa.banchan.domain.usecase.order

import com.woowa.banchan.domain.repository.OrderRepository
import javax.inject.Inject

class GetOrderInfoUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {

    operator fun invoke() = orderRepository.getAllOrderWithPaging()
}
