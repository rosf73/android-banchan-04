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
            var totalPrice = it.price.toMoneyInt() * it.quantity
            if (totalPrice < 40000) totalPrice += 2500
            orderLineItem.add(
                OrderLineItem(
                    name = it.name,
                    imageUrl = it.imageUrl,
                    quantity = it.quantity,
                    price = totalPrice.toMoneyString()
                )
            )
        }

        return orderRepository.addOrder(order, *orderLineItem.toTypedArray())
    }
}