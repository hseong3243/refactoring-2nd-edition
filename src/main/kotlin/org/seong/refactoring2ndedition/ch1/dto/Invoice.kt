package org.seong.refactoring2ndedition.ch1.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class Invoice(
    val customer: String,
    val performances: List<Performance>
)

@Serializable
data class Performance(val playID: String, val audience: Int, var play: Play? = null, var amount: Int = 0, var volumeCredits: Int = 0)

fun getInvoices(): List<Invoice> {
    return Json.decodeFromString<List<Invoice>>(invoicesJson)
}

private const val invoicesJson = """
        [
            {
                "customer": "BigCo",
                "performances": [
                    {
                        "playID": "hamlet",
                        "audience": 55
                    },
                    {
                        "playID": "as-like",
                        "audience": 35
                    },
                    {
                        "playID": "othello",
                        "audience": 40
                    }
                ]
            }
        ]
    """
