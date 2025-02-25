package org.seong.refactoring2ndedition.ch6.extract_method

import java.time.LocalDate

fun main() {
    val printOwing = PrintOwing()
    printOwing.printOwing(Invoice(listOf(Order(1000)), LocalDate.now(), "고객"))
}

class PrintOwing {

    fun printOwing(invoice: Invoice) {
        var outstanding = 0;

        println("**********")
        println("** 고객 채무 **")
        println("**********")

        // 미해결 채무(outstanding)를 계산한다.
        for (o in invoice.orders) {
            outstanding += o.amount
        }

        // 마감일(dueDate)을 기록한다.
        val today = Clock.today()
        invoice.dueDate = LocalDate.of(today.year, today.month, today.dayOfMonth).plusDays(30)

        // 세부 사항을 출력한다.
        println("고객명: ${invoice.customer}")
        println("채무액: $outstanding")
        println("마감일: ${invoice.dueDate}")
    }
}

data class Invoice(
    val orders: List<Order>,
    var dueDate: LocalDate,
    val customer: String,
)

data class Order(
    val amount: Int
)

class Clock {
    companion object {
        fun today(): LocalDate {
            return LocalDate.now()
        }
    }
}
