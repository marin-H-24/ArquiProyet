package com.marin.arquiproyet.data.models

data class Project(
    val name: String,
    val category: String,
    val date: String,
    val iconUriString: String? = null,
    val apartados: Map<String, String> = mapOf(
        "Dashboard" to "",
        "ENTRADA" to "",
        "ESTADISTIC" to ""
    ),
    val notas: List<String> = emptyList(),
    val ideasFuturas: List<String> = emptyList()
)