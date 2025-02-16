package org.seong.refactoring2ndedition.ch4

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
}
