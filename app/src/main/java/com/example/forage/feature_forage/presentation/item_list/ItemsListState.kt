package com.example.forage.feature_forage.presentation.item_list

import com.example.forage.feature_forage.domain.model.ForageItemWithImage
import com.example.forage.feature_forage.domain.util.ForageItemOrder
import com.example.forage.feature_forage.domain.util.OrderType

data class ItemsListState(
    val items: List<ForageItemWithImage> = emptyList(),
    val itemOrder: ForageItemOrder = ForageItemOrder.Name(OrderType.Descending),
    val displayOnlyInSeason: Boolean = true
)
