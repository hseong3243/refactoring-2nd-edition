package org.seong.refactoring2ndedition

import org.seong.refactoring2ndedition.dto.Performance
import org.seong.refactoring2ndedition.dto.Play

open class PerformanceCalculator(
    val performance: Performance,
    val play: Play
) {
    companion object {
        fun createPerformanceCalculator(
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

    open fun amount(): Int {
        var result = 0
        when (this.performance.play?.type) {
            "tragedy" -> throw UnsupportedOperationException("오류 발생")
            "comedy" -> throw UnsupportedOperationException("오류 발생")

            else -> {
                throw Exception("알 수 없는 장르: ${this.performance.play?.type}")
            }
        }
        return result
    }

    fun volumeCredits(): Int {
        var result = Math.max(this.performance.audience - 30, 0)
        // 희극 관객 5명마다 추가 포인트를 제공한다.
        if ("comedy" == this.play.type)
            result += Math.floor((this.performance.audience / 5).toDouble()).toInt();
        return result;
    }
}
