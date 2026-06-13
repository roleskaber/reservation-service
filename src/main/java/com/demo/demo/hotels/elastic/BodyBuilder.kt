package com.demo.demo.hotels.elastic
import com.fasterxml.jackson.databind.ObjectMapper

fun getQueryObj(hint: String, page: Int?, size: Int?): String {
    val root = mutableMapOf<String, Any>(
        "query" to mapOf(
            "query_string" to mapOf(
                "query" to hint
            )
        )
    )
    if (page != null && size != null && page != 1) root["from"] = page * size
    if (size != null) root["size"] = size
    return ObjectMapper().writeValueAsString(root)
}