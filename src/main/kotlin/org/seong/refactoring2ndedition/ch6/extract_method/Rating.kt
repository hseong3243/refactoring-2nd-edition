package org.seong.refactoring2ndedition.ch6.extract_method


class Rating {

    fun rating(aDriver: Driver): Int {
        return if (moreThanFiveLateDeliveries(aDriver)) 2 else 1
    }

    private fun moreThanFiveLateDeliveries(aDriver: Driver): Boolean {
        return aDriver.numberOfLateDeliveries > 5;
    }
}

data class Driver(
    val numberOfLateDeliveries: Int,
)
