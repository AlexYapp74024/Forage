package com.example.forage.feature_forage.domain.use_case

import android.graphics.Bitmap
import com.example.forage.core.image_processing.ImageRepository
import com.example.forage.feature_forage.domain.model.ForageItem
import com.example.forage.feature_forage.domain.model.ForageItemWithImage
import com.example.forage.feature_forage.domain.repository.ForageItemRepository
import com.example.forage.feature_forage.domain.util.ForageItemOrder
import com.example.forage.feature_forage.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllForageItems(
    private val repository: ForageItemRepository,
    private val imageRepository: ImageRepository
) {
    operator fun invoke(
        itemOrder: ForageItemOrder = ForageItemOrder.Name(OrderType.Ascending),
        onlyInSeason: Boolean = true,
    ): Flow<List<ForageItem>> {
        return repository.getAllItem().map { items ->
            items.filter { !onlyInSeason || it.inSeason }
                .sortOrder(itemOrder)
        }
    }

    suspend fun withImages(
        itemOrder: ForageItemOrder = ForageItemOrder.Name(OrderType.Ascending),
        onlyInSeason: Boolean = true
    ): Flow<Map<ForageItem, Flow<Bitmap?>>> =
        this(itemOrder = itemOrder, onlyInSeason = onlyInSeason).map { items ->
            mutableMapOf<ForageItem, Flow<Bitmap?>>().also { map ->
                items.map { item ->
                    ForageItemWithImage(item)
                }.map {
                    map[it.item] = it.loadImage(imageRepository)
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