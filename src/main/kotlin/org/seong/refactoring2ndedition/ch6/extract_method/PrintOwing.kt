package org.seong.refactoring2ndedition.ch6.extract_method

import java.time.LocalDate

fun main() {
    val printOwing = PrintOwing()
    printOwing.printOwing(Invoice(listOf(Order(1000)), LocalDate.now(), "고객"))
}

class PrintOwing {

    fun printOwing(invoice: Invoice) {
        var outstanding = 0;

        printBanner()

        // 미해결 채무(outstanding)를 계산한다.
        for (o in invoice.orders) {
            outstanding += o.amount
        }

        // 마감일(dueDate)을 기록한다.
        recordDueDate(invoice)

        // 세부 사항을 출력한다.
        printDetails(invoice, outstanding)
    }

    private fun printBanner() {
        println("**********")
        println("** 고객 채무 **")
        println("**********")
    }

    private fun recordDueDate(invoice: Invoice) {
        val today = Clock.today()
        invoice.dueDate = LocalDate.of(today.year, today.month, today.dayOfMonth).plusDays(30)
    }

    private fun printDetails(
        invoice: Invoice,
        outstanding: Int
    ) {
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

/**
 * 시스템 시계를 감싸는 래퍼 클래스
 * now()와 같이 직접 호출할 경우 테스트할 때 마다 결과가 달라지는 것을 막는다.
 *
 * todo: 추후 마틴 파울러의 블로그를 참조하여 어떤 방식으로 막을지 찾아보도록 한다.
 */
class Clock {
    companion object {
        fun today(today: LocalDate = LocalDate.now()): LocalDate {
            return today
        }
    }
}
