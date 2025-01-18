package org.seong.refactoring2ndedition.ch1.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class Play(
    val name: String,
    val type: String
)

fun getPlayMap(): Map<String, Play> {
    return Json.decodeFromString<Map<String, Play>>(playsJson)
}

private const val playsJson = """
        {
            "hamlet": {"name": "Hamlet", "type": "tragedy"},
            "as-like": {"name": "As You Like It", "type": "comedy"},
            "othello": {"name": "Othello", "type": "tragedy"}
        }
    """
