package org.seong.refactoring2ndedition.ch6.inline_method

class ReportLines {

    fun reportLines(aCustomer: Customer) : Map<String, String> {
        val lines = mutableMapOf<String, String>()
        lines["name"] = aCustomer.name
        gatherCustomerData(lines, aCustomer);
        return lines
    }

    private fun gatherCustomerData(out: MutableMap<String, String>, aCustomer: Customer) {
        out["location"] = aCustomer.location
    }
}

data class Customer(
    val name: String,
    val location: String,
)
