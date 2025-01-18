package org.seong.refactoring2ndedition

import org.seong.refactoring2ndedition.dto.Invoice
import org.seong.refactoring2ndedition.dto.Play

class Statement {
    companion object {
        fun statement(invoice: Invoice, plays: Map<String, Play>): String {
            return renderHtml(StatementData.createStatementData(plays, invoice))
        }

        private fun renderHtml(
            data: StatementData
        ): String {

            fun usd(aNumber: Int): String {
                return String.format("$${aNumber / 100.0}")
            }

            val result = StringBuilder()
            result.append("<h1>청구 내역 (고객명: ${data.customer})</h1>\n")
            result.append("<table>\n")
            result.append("<tr><th>연극</th><th>좌석 수</th><th>금액</th></tr>\n")
            for (perf in data.performances) {
                result.append("  <tr><td>${perf.play?.name}</td><td>(${perf.audience}석)</td>")
                result.append("<td>${usd(perf.amount)}</td></tr>\n")
            }
            result.append("</table>\n")
            result.append("<p>총액: <em>${usd(data.totalAmount)}</em></p>\n")
            result.append("<p>적립 포인트: <em>${data.totalVolumeCredits}</em>점</p>\n")
            return result.toString()
        }
    }
}
