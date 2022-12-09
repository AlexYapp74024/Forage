package com.example.forage.feature_forage.domain.model

import android.content.Context
import android.graphics.Bitmap
import com.example.forage.core.image_processing.Image
import com.example.forage.core.image_processing.InternalImageRepository

data class ForageItemWithImage(
    val item: ForageItem,
    val bitmap: Bitmap? = null
) {
    suspend fun loadImage(context: Context, onImageReceived: (Bitmap) -> Unit) {
        InternalImageRepository(context).loadImage(item.imagePath) { bitmap ->
            onImageReceived(bitmap)
        }
    }

    suspend fun saveImage(context: Context) {
        bitmap?.let {
            InternalImageRepository(context).saveImage(Image(item.imagePath, it))
        }
    }
}