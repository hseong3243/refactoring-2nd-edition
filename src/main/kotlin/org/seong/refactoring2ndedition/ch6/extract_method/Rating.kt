package org.seong.refactoring2ndedition.ch6.extract_method

class Rating {

    fun rating(aDriver: Driver): Int {
        return if (aDriver.numberOfLateDeliveries > 5) 2 else 1
    }
}

data class Driver(
    val numberOfLateDeliveries: Int,
)
