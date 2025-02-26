package org.seong.refactoring2ndedition.ch6.inline_method

class ReportLines {

    fun reportLines(aCustomer: Customer) : Map<String, String> {
        val lines = mutableMapOf<String, String>()
        lines["name"] = aCustomer.name
        lines["location"] = aCustomer.location
        return lines
    }
}

data class Customer(
    val name: String,
    val location: String,
)
