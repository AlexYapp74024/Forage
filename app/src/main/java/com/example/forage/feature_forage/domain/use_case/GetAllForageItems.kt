package com.example.forage.feature_forage.domain.use_case

import android.content.Context
import android.graphics.Bitmap
import com.example.forage.feature_forage.domain.model.ForageItem
import com.example.forage.feature_forage.domain.model.ForageItemWithImage
import com.example.forage.feature_forage.domain.repository.ForageItemRepository
import com.example.forage.feature_forage.domain.util.ForageItemOrder
import com.example.forage.feature_forage.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllForageItems(
    private val repository: ForageItemRepository
) {
    operator fun invoke(
        itemOrder: ForageItemOrder = ForageItemOrder.Name(OrderType.Ascending),
        onlyInSeason: Boolean = true,
    ): Flow<List<ForageItem>> {
        return repository.getAll().map { items ->
            items.filter { !onlyInSeason || it.inSeason }
                .sortOrder(itemOrder)
        }
    }

    suspend fun withImages(
        context: Context,
        itemOrder: ForageItemOrder = ForageItemOrder.Name(OrderType.Ascending),
        onlyInSeason: Boolean = true,
        onImageUpdate: (ForageItem, Bitmap?) -> Unit,
    ) {
        this(
            itemOrder = itemOrder, onlyInSeason = onlyInSeason
        ).collect { items ->
            items.forEach { item ->
                onImageUpdate(item, null)
                ForageItemWithImage(item).loadImage(context) { bmp ->
                    onImageUpdate(item, bmp)
                }
            }
        }
    }

    private fun List<ForageItem>.sortOrder(
        sortType: ForageItemOrder,
    ) = when (sortType.orderType) {
        is OrderType.Ascending -> this.sortedBy { sortBy(it, sortType) }
        is OrderType.Descending -> this.sortedByDescending { sortBy(it, sortType) }
    }

    private fun sortBy(
        item: ForageItem,
        sortType: ForageItemOrder,
    ) = when (sortType) {
        is ForageItemOrder.Location -> item.location.lowercase()
        is ForageItemOrder.Name -> item.name.lowercase()
    }

}