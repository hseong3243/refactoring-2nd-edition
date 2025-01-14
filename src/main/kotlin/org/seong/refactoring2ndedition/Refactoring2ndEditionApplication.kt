package org.seong.refactoring2ndedition

import org.seong.refactoring2ndedition.dto.Invoice
import org.seong.refactoring2ndedition.dto.Play
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Refactoring2ndEditionApplication

fun main(args: Array<String>) {
    runApplication<Refactoring2ndEditionApplication>(*args)
}

fun statement(invoice: Invoice, plays: Map<String, Play>) {
    
}
