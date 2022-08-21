package com.woowa.banchan.domain.usecase.order

import com.woowa.banchan.domain.entity.Cart
import com.woowa.banchan.domain.entity.OrderDetailSection.*
import com.woowa.banchan.domain.repository.OrderRepository
import com.woowa.banchan.extensions.toMoneyInt
import com.woowa.banchan.extensions.toMoneyString
import javax.inject.Inject

class AddOrderUserCase @Inject constructor(
    private val orderRepository: OrderRepository
) {

    suspend operator fun invoke(
        carts: List<Cart>,
        orderedAt: Long
    ): Long {
        val order = Order(
            orderedAt = orderedAt,
            status = "START"
        )

        val orderLineItem = mutableListOf<OrderLineItem>()
        carts.forEach {
            orderLineItem.add(
                OrderLineItem(
                    name = it.name,
                    imageUrl = it.imageUrl,
                    quantity = it.quantity,
                    price = (it.price.toMoneyInt() * it.quantity).toMoneyString()
                )
            )
        }

        return orderRepository.addOrder(order, *orderLineItem.toTypedArray())
    }
}