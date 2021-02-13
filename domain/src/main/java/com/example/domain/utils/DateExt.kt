package com.example.domain.utils

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date

fun Date.toFormattedDate(
    format: String,
    locale: Locale = Locale.getDefault()
): String = SimpleDateFormat(format, locale).format(this)