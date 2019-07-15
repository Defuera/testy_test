package com.justd.rabo.app.model

data class Issue (
    val firstame: String,
    val surname: String,
    val issueCount: Int,
    val dateOfBirth: Date

)

typealias Date = String