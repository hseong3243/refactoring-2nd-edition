package org.seong.refactoring2ndedition.ch4

class Producer(
    private var province: Province,
    val name: String,
    cost: Int,
    production: Int,
) {
    var production: Int = production
        private set
    var cost: Int = cost
        private set

    fun cost(arg: String) {
        this.cost = arg.toInt()
    }

    fun production(amountStr: String) {
        val amount: Int? = amountStr.toIntOrNull()
        val newProduction = amount ?: 0
        this.province.totalProduction += newProduction - this.production
        this.production = newProduction
    }
}
