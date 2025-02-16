package org.seong.refactoring2ndedition.ch4

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

fun getDocumentDate(): Document {
    return Json.decodeFromString<Document>(JSON_DATA)
}

private const val JSON_DATA = """
    {
        "name": "Asia",
        "producers": [
            {"name": "Byzantium", "cost":10, "production": 9},
            {"name": "Attalia", "cost":12, "production": 10},
            {"name": "Sinope", "cost":10, "production": 6}
        ],
        "demand": 30,
        "price": 20
    }
"""

@Serializable
data class Document(
    val name: String,
    val producers: Map<String, String>,
    val demand: Int,
    val price: Int,
)
