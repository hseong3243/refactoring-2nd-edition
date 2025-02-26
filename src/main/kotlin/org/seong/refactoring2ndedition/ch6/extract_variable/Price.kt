package org.seong.refactoring2ndedition.ch6.extract_variable

import kotlin.math.max
import kotlin.math.min

class Price {

    fun price(order: Order) : Double {
        val basePrice = order.quantity * order.itemPrice
        val quantityDiscount = max(0, order.quantity - 500) * order.itemPrice * 0.05
        val shipping = min(basePrice * 0.1, 100.0)
        return basePrice - quantityDiscount + shipping
    }
}

data class Order(
    val quantity: Int,
    val itemPrice: Int,
)
