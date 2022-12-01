package com.example.forage.feature_forage.domain.util

sealed class ForageItemOrder(val orderType: OrderType)  {
    class Name (orderType: OrderType): ForageItemOrder(orderType)
    class Location (orderType: OrderType): ForageItemOrder(orderType)
}
