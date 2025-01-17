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

private data class Data(
    var customer: String = "",
    var performances: List<Performance> = emptyList()
)

fun statement(invoice: Invoice, plays: Map<String, Play>): String {
    fun playFor(aPerformance: Performance): Play? {
        return plays[aPerformance.playID]
    }

    fun amountFor(
        aPerformance: Performance
    ): Int {
        var result = 0
        when (aPerformance.play?.type) {
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
                throw Exception("알 수 없는 장르: ${aPerformance.play?.type}")
            }
        }
        return result
    }

    fun enrichPerformance(performance: Performance): Performance {
        performance.play = playFor(performance)
        performance.amount = amountFor(performance)
        return performance
    }

    val data = Data()
    data.customer = invoice.customer
    data.performances = invoice.performances.map {
        enrichPerformance(it)
    }
    return renderPlainText(data)
}


private fun renderPlainText(
    data: Data
): String {

    fun volumeCreditsFor(aPerformance: Performance): Int {
        var result = Math.max(aPerformance.audience - 30, 0)
        // 희극 관객 5명마다 추가 포인트를 제공한다.
        if ("comedy" == aPerformance.play?.type)
            result += Math.floor((aPerformance.audience / 5).toDouble()).toInt();
        return result;
    }

    fun usd(aNumber: Int): String {
        return String.format("$${aNumber / 100.0}")
    }

    fun totalAmount(): Int {
        var result = 0
        for (perf in data.performances) {
            result += perf.amount
        }
        return result
    }

    fun totalVolumeCredits(): Int {
        var result = 0
        for (perf in data.performances) {
            result += volumeCreditsFor(perf)
        }
        return result;
    }


    var result = "청구 내역 (고객명: ${data.customer})\n"
    for (perf in data.performances) {
        // 청구 내역을 출력한다.
        result += " ${perf.play?.name}: ${usd(perf.amount)} (${perf.audience}석)\n"

    }
    result += "총액: ${usd(totalAmount())}\n"
    result += "적립 포인트: ${totalVolumeCredits()}점\n"
    return result;
}

