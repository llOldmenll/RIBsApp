package com.example.domain.utils

import java.text.SimpleDateFormat
import java.util.Locale

fun String.toLowerCaseDefault(): String = this.toLowerCase(Locale.getDefault())

fun String.toAlphaNumeric(): String = this.replace("[^\\s\\p{L}\\p{N}]".toRegex(), "")

// Input date sample - 2021-06-25T06:25:00.000
// Output date sample - 25.06.2021 6:25
fun String.toFormattedDate(locale: Locale = Locale.getDefault()): String {
    val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", locale)
    val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm", locale)
    return formatter.format(parser.parse(this))
}