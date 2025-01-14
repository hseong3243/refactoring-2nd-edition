package org.seong.refactoring2ndedition

import org.seong.refactoring2ndedition.dto.Invoice
import org.seong.refactoring2ndedition.dto.Play

class Refactoring2ndEditionApplication

fun main() {



}

fun statement(invoice: Invoice, plays: Map<String, Play>): String {
    var totalAmount = 0
    var volumeCredits = 0
    var result = "청구 내역 (고객명: ${invoice.customer}\n"

    for (perf in invoice.performances) {
        val play = plays.get(perf.playID)
        var thisAmount = 0

        when (play!!.type) {
            "tragedy" -> { // 비극
                thisAmount = 40000
                if (perf.audience > 30) {
                    thisAmount += 1000 * (perf.audience - 30)
                }
            }

            "comedy" -> { // 희극
                thisAmount = 30000;
                if (perf.audience > 20) {
                    thisAmount += 10000 + 500 * (perf.audience - 20);
                }
                thisAmount += 300 * perf.audience
            }
            else -> {
                throw Exception("알 수 없는 장르: ${play.type}")
            }
        }

        // 포인트를 적립한다.
        volumeCredits += Math.max(perf.audience - 30, 0)
        // 희극 관객 5명마다 추가 포인트를 제공한다.
        if ("comedy" == play.type) volumeCredits += Math.floor((perf.audience / 5).toDouble()).toInt();

        // 청구 내역을 출력한다.
        result += " ${play.name}: ${thisAmount/100} (${perf.audience}석)\n"
        totalAmount += thisAmount
    }
    result += "총액: ${totalAmount/100}\n"
    result += "적립 포인트: ${volumeCredits}점\n"
    return result;
}
