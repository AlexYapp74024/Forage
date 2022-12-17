package com.example.forage.feature_forage.domain.use_case

import com.example.forage.core.image_processing.ImageRepository
import com.example.forage.feature_forage.domain.model.ForageItem
import com.example.forage.feature_forage.domain.model.ForageItemWithImage
import com.example.forage.feature_forage.domain.repository.ForageItemRepository

class AddForageItem(
    private val repository: ForageItemRepository,
    private val imageRepository: ImageRepository
) {
    suspend operator fun invoke(forageItem: ForageItem): Long {
        with(forageItem) {
            if (name.isEmpty()) throw IllegalArgumentException("Name Cannot Be Empty")
        }
        return repository.insertItem(forageItem)
    }

    suspend operator fun invoke(
        forageItem: ForageItemWithImage,
        imageUpdated: Boolean = true,
    ) {
        val newID: Long = this(forageItem.item)

        forageItem.copy(item = forageItem.item.copy(id = newID.toInt())).run {
            if (imageUpdated) saveImage(imageRepository)
        }
    }
}