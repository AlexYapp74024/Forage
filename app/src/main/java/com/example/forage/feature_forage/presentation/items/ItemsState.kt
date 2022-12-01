package com.example.forage.feature_forage.presentation.items

import com.example.forage.feature_forage.domain.model.ForageItem
import com.example.forage.feature_forage.domain.util.ForageItemOrder
import com.example.forage.feature_forage.domain.util.OrderType

data class ItemsState(
    val items: List<ForageItem> = emptyList(),
    val itemOrder: ForageItemOrder = ForageItemOrder.Name(OrderType.Descending),
    val displayOnlyInSeason: Boolean = true
)
