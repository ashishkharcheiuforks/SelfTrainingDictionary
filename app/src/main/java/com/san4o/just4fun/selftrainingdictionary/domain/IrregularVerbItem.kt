package com.san4o.just4fun.selftrainingdictionary.domain

data class IrregularVerbItem(
    val id: Long,
    val translation: String,
    val present: String,
    val past: String,
    val perfect: String
) {
}