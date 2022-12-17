package com.example.forage.core.image_processing

import android.graphics.Bitmap
import com.canhub.cropper.CropImage.CancelledResult.bitmap
import kotlinx.coroutines.flow.Flow

abstract class EntityWithImage {
    abstract val imagePath: String

    suspend fun loadImage(
        imageRepository: ImageRepository
    ): Flow<Bitmap?> {
        return imageRepository.loadImage(imagePath)
    }

    suspend fun saveImage(imageRepository: ImageRepository) {
        bitmap?.let {
            imageRepository.saveImage(imagePath, it)
        }
    }
}