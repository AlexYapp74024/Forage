package com.example.forage.feature_forage.domain.util

sealed class OrderType {
    object Ascending : OrderType()
    object Descending : OrderType()
}
