package org.seong.refactoring2ndedition.ch6.extract_variable

import kotlin.math.max
import kotlin.math.min

class Price {

    fun price(order: Order) : Double {
        // 가격(price) = 기본 가격 - 수량 할인 + 배송비
        val basePrice = order.quantity * order.itemPrice
        return basePrice -
                max(0, order.quantity-500) * order.itemPrice * 0.05 +
                min(order.quantity * order.itemPrice * 0.1, 100.0)
    }
}

data class Order(
    val quantity: Int,
    val itemPrice: Int,
)
