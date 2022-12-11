package com.example.forage.feature_forage.domain.model

import android.graphics.Bitmap
import com.example.forage.core.image_processing.ImageRepository

data class ForageItemWithImage(
    val item: ForageItem,
    val bitmap: Bitmap? = null
) {
    suspend fun loadImage(
        imageRepository: ImageRepository,
        onImageReceived: (Bitmap) -> Unit
    ) {
        imageRepository.loadImage(imagePath) { bitmap ->
            onImageReceived(bitmap)
        }
    }

    suspend fun saveImage(imageRepository: ImageRepository) {
        bitmap?.let {
            imageRepository.saveImage(imagePath, it)
        }
    }
}

val ForageItemWithImage.imagePath
    get() = "${item.name}${item.id}.png"