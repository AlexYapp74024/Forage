package com.example.forage.feature_forage.domain.use_case

import com.example.forage.core.image_processing.ImageRepository
import com.example.forage.feature_forage.domain.model.ForageItem
import com.example.forage.feature_forage.domain.model.ForageItemWithImage
import com.example.forage.feature_forage.domain.repository.ForageItemRepository

class AddForageItem(
    private val repository: ForageItemRepository,
    private val imageRepository: ImageRepository
) {
    suspend operator fun invoke(forageItem: ForageItem) {
        repository.insertItem(forageItem)
    }

    suspend operator fun invoke(forageItem: ForageItemWithImage) {
        forageItem.run {
            this@AddForageItem(item)
            saveImage(imageRepository)
        }
    }
}