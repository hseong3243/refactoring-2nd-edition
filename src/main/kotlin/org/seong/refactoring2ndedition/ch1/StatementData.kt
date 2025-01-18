package org.seong.refactoring2ndedition.ch1

import org.seong.refactoring2ndedition.ch1.dto.Invoice
import org.seong.refactoring2ndedition.ch1.dto.Performance
import org.seong.refactoring2ndedition.ch1.dto.Play

data class StatementData(
    var customer: String = "",
    var performances: List<Performance> = emptyList(),
    var totalAmount: Int = 0,
    var totalVolumeCredits: Int = 0
) {
    companion object {
        fun createStatementData(plays: Map<String, Play>, invoice: Invoice): StatementData {
            fun playFor(aPerformance: Performance): Play? {
                return plays[aPerformance.playID]
            }

            fun totalVolumeCredits(data: StatementData): Int =
                data.performances.map { it.volumeCredits }
                    .reduce { total, volumeCredits -> total + volumeCredits }

            fun totalAmount(data: StatementData): Int =
                data.performances.map { it.amount }
                    .reduce { total, amount -> total + amount }

            fun enrichPerformance(performance: Performance): Performance {
                val calculator = PerformanceCalculator.create(performance, playFor(performance)!!)
                performance.play = calculator.play
                performance.amount = calculator.amount()
                performance.volumeCredits = calculator.volumeCredits()
                return performance
            }

            val statementData =
                StatementData(invoice.customer, invoice.performances.map { enrichPerformance(it) })
            statementData.customer = invoice.customer
            statementData.performances = invoice.performances.map {
                enrichPerformance(it)
            }
            statementData.totalAmount = totalAmount(statementData)
            statementData.totalVolumeCredits = totalVolumeCredits(statementData)
            return statementData
        }
    }
}
