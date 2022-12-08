package com.example.forage.feature_forage.domain.model

import android.content.Context
import android.graphics.Bitmap
import com.example.forage.core.image_processing.Image
import com.example.forage.core.image_processing.InternalImageRepository
import java.io.IOException

data class ForageItemWithImage(
    val item: ForageItem,
    val bitmap: Bitmap? = null
) {
    suspend fun loadImage(context: Context): ForageItemWithImage {
        val bitmap = try {
            InternalImageRepository(context).loadImage(item.imagePath).bitmap
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }

        return ForageItemWithImage(item, bitmap)
    }

    suspend fun saveImage(context: Context) {
        bitmap?.let {
            InternalImageRepository(context).saveImage(Image(item.imagePath, it))
        }
    }
}