package org.seong.refactoring2ndedition.ch4

import kotlin.math.min

class Province(
    val name: String,
    val producers: List<Producer>,
    var totalProduction: Int,
    private var demand: Int,
    private var price: Int,
) {
    fun demand(arg: String) {
        this.demand = arg.toInt()
    }

    fun price(arg: String) {
        this.price = arg.toInt()
    }

    fun getProfit(): Int {
        return this.getDemandValue() - this.getDemandCost()
    }

    private fun getDemandValue(): Int {
        return this.getSatisfiedDemand() * this.price
    }

    private fun getSatisfiedDemand(): Int {
        return min(this.demand, this.totalProduction)
    }

    private fun getDemandCost(): Int {
        var remainingDemand = this.demand
        var result = 0
        this.producers
            .sortedBy { it.cost }
            .forEach {
                val contribution = min(remainingDemand, it.production)
                remainingDemand -= contribution
                result += contribution * it.cost
            }
        return result
    }
}
