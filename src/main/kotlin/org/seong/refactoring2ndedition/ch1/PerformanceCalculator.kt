package org.seong.refactoring2ndedition.ch1

import org.seong.refactoring2ndedition.ch1.dto.Performance
import org.seong.refactoring2ndedition.ch1.dto.Play

abstract class PerformanceCalculator(
    val performance: Performance,
    val play: Play
) {
    companion object {
        fun create(
            performance: Performance,
            play: Play
        ): PerformanceCalculator {
            return when (play.type) {
                "tragedy" -> TragedyCalculator(performance, play)
                "comedy" -> ComedyCalculator(performance, play)
                else -> throw Exception("알 수 없는 장르: ${play.type}")
            }
        }
    }

    abstract fun amount(): Int

    open fun volumeCredits(): Int {
        return Math.max(this.performance.audience - 30, 0)
    }
}
