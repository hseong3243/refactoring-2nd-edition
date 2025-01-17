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
    return renderPlainText(StatementData.createStatementData(plays, invoice))
}

private fun renderPlainText(
    statementData: StatementData
): String {

    fun usd(aNumber: Int): String {
        return String.format("$${aNumber / 100.0}")
    }

    var result = "청구 내역 (고객명: ${statementData.customer})\n"
    for (perf in statementData.performances) {
        // 청구 내역을 출력한다.
        result += " ${perf.play?.name}: ${usd(perf.amount)} (${perf.audience}석)\n"

    }
    result += "총액: ${usd(statementData.totalAmount)}\n"
    result += "적립 포인트: ${statementData.totalVolumeCredits}점\n"
    return result;
}

