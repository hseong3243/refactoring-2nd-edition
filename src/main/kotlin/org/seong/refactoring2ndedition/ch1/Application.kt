package org.seong.refactoring2ndedition.ch1

import org.seong.refactoring2ndedition.ch1.dto.getInvoices
import org.seong.refactoring2ndedition.ch1.dto.getPlayMap

class Application

fun main() {
    val invoices = getInvoices()
    val playMap = getPlayMap()

    for (invoice in invoices) {
        val result = Statement.statement(invoice, playMap)
        println(result)
    }
}
