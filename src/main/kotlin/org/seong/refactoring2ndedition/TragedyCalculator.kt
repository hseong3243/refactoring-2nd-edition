package org.seong.refactoring2ndedition

import org.seong.refactoring2ndedition.dto.Performance
import org.seong.refactoring2ndedition.dto.Play

class TragedyCalculator(
    performance: Performance,
    play: Play
) : PerformanceCalculator(performance, play) {
    override fun amount(): Int {
        var result = 40000
        if (this.performance.audience > 30) {
            result += 1000 * (this.performance.audience - 30)
        }
        return result;
    }
}
