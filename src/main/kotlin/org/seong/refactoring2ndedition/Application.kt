package org.seong.refactoring2ndedition

import org.seong.refactoring2ndedition.dto.*

class Application

fun main() {
    val invoices = getInvoices()
    val playMap = getPlayMap()

    for (invoice in invoices) {
        val result = statement(invoice, playMap)
        println(result)
    }
}

fun statement(invoice: Invoice, plays: Map<String, Play>): String {
    fun playFor(aPerformance: Performance): Play? {
        return plays[aPerformance.playID]
    }
    fun amountFor(
        aPerformance: Performance
    ): Int {
        var result = 0
        when (playFor(aPerformance)?.type) {
            "tragedy" -> { // 비극
                result = 40000
                if (aPerformance.audience > 30) {
                    result += 1000 * (aPerformance.audience - 30)
                }
            }

            "comedy" -> { // 희극
                result = 30000;
                if (aPerformance.audience > 20) {
                    result += 10000 + 500 * (aPerformance.audience - 20);
                }
                result += 300 * aPerformance.audience
            }

            else -> {
                throw Exception("알 수 없는 장르: ${playFor(aPerformance)?.type}")
            }
        }
        return result
    }

    var totalAmount = 0
    var volumeCredits = 0
    var result = "청구 내역 (고객명: ${invoice.customer})\n"
    fun format(totalAmount: Int): String {
        return String.format("$${totalAmount.toDouble()}")
    }

    for (perf in invoice.performances) {
        // 포인트를 적립한다.
        volumeCredits += Math.max(perf.audience - 30, 0)
        // 희극 관객 5명마다 추가 포인트를 제공한다.
        if ("comedy" == playFor(perf)?.type) volumeCredits += Math.floor((perf.audience / 5).toDouble())
            .toInt();

        // 청구 내역을 출력한다.
        result += " ${playFor(perf)?.name}: ${format(amountFor(perf) / 100)} (${perf.audience}석)\n"
        totalAmount += amountFor(perf)
    }
    result += "총액: ${format(totalAmount / 100)}\n"
    result += "적립 포인트: ${volumeCredits}점\n"
    return result;
}
