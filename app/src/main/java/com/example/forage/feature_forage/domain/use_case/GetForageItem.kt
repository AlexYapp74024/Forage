package com.example.forage.feature_forage.domain.use_case

import android.content.Context
import com.example.forage.feature_forage.domain.model.ForageItem
import com.example.forage.feature_forage.domain.model.ForageItemWithImage
import com.example.forage.feature_forage.domain.repository.ForageItemRepository
import kotlinx.coroutines.flow.map

class GetForageItem(
    private val repository: ForageItemRepository
) {
    operator fun invoke(id: Int) = repository.getItem(id)

    suspend fun withImage(
        id: Int,
        context: Context,
        onImageUpdate: (ForageItemWithImage) -> Unit,
    ) {
        this(id).map { it ?: ForageItem() }
            .map { ForageItemWithImage(it) }
            .collect { imageItem ->
                onImageUpdate(imageItem)
                imageItem.loadImage(context) { bitmap ->
                    onImageUpdate(imageItem.copy(bitmap = bitmap))
                }
            }
    }
}