package com.example.forage.feature_forage.domain.use_case

import com.example.forage.core.image_processing.ImageRepository
import com.example.forage.feature_forage.domain.model.ForageItem
import com.example.forage.feature_forage.domain.model.ForageItemWithImage
import com.example.forage.feature_forage.domain.repository.ForageItemRepository
import kotlinx.coroutines.flow.map

class GetForageItem(
    private val repository: ForageItemRepository,
    private val imageRepository: ImageRepository
) {
    operator fun invoke(id: Int) = repository.getItem(id)

    suspend fun withImage(
        id: Int,
        onImageUpdate: (ForageItemWithImage) -> Unit,
    ) {
        this(id).map { it ?: ForageItem() }
            .map { ForageItemWithImage(it) }
            .collect { imageItem ->
                onImageUpdate(imageItem)
                imageItem.loadImage(imageRepository) { bitmap ->
                    onImageUpdate(imageItem.copy(bitmap = bitmap))
                }
            }
    }
}