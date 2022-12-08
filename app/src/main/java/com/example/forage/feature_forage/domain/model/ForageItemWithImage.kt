package com.example.forage.feature_forage.domain.model

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.forage.core.image_processing.Image
import com.example.forage.core.image_processing.InternalImageRepository

data class ForageItemWithImage(
    val item: ForageItem,
    val bitmap: MutableState<Bitmap?> = mutableStateOf<Bitmap?>(null)
) {
    suspend fun loadImage(context: Context): ForageItemWithImage {
        val bitmap = InternalImageRepository(context).loadImage(item.imagePath)
        return ForageItemWithImage(item, bitmap)
    }

    suspend fun saveImage(context: Context) {
        bitmap.value?.let {
            InternalImageRepository(context).saveImage(Image(item.imagePath, it))
        }
    }
}