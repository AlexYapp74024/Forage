package com.example.forage.feature_forage.domain.model

import android.content.Context
import android.graphics.Bitmap
import com.example.forage.core.image_processing.InternalImageRepository

data class ForageItemWithImage(
    val item: ForageItem,
    val bitmap: Bitmap? = null
) {
    suspend fun loadImage(context: Context, onImageReceived: (Bitmap) -> Unit) {
        InternalImageRepository(context).loadImage(imagePath) { bitmap ->
            onImageReceived(bitmap)
        }
    }

    suspend fun saveImage(context: Context) {
        bitmap?.let {
            InternalImageRepository(context).saveImage(imagePath, it)
        }
    }
}

val ForageItemWithImage.imagePath
    get() = "${item.name}${item.id}.png"