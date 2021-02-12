package com.example.data.entity.flight

data class FareEntity(
    val type: String? = null,
    val amount: Double? = null,
    val count: Int? = null,
    val hasDiscount: Boolean? = null,
    val publishedFare: Double? = null,
    val discountAmount: Double? = null,
    val discountInPercent: Int? = null
)