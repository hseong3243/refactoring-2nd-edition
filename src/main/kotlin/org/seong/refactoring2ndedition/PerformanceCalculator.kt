package org.seong.refactoring2ndedition

import org.seong.refactoring2ndedition.dto.Performance
import org.seong.refactoring2ndedition.dto.Play

class PerformanceCalculator(
    val performance: Performance,
    val play: Play
) {

    fun amount(): Int {
        var result = 0
        when (this.performance.play?.type) {
            "tragedy" -> { // 비극
                result = 40000
                if (this.performance.audience > 30) {
                    result += 1000 * (this.performance.audience - 30)
                }
            }

            "comedy" -> { // 희극
                result = 30000;
                if (this.performance.audience > 20) {
                    result += 10000 + 500 * (this.performance.audience - 20);
                }
                result += 300 * this.performance.audience
            }

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
