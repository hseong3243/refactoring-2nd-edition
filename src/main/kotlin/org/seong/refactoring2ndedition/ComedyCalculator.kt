package org.seong.refactoring2ndedition

import org.seong.refactoring2ndedition.dto.Performance
import org.seong.refactoring2ndedition.dto.Play

class ComedyCalculator(
    performance: Performance,
    play: Play
) : PerformanceCalculator(performance, play) {

    override fun amount(): Int {
        var result = 30000;
        if (this.performance.audience > 20) {
            result += 10000 + 500 * (this.performance.audience - 20);
        }
        result += 300 * this.performance.audience
        return result
    }

    override fun volumeCredits(): Int {
        return super.volumeCredits() + Math.floor((this.performance.audience / 5.0)).toInt()
    }
}
