package com.example.forage.core.image_processing

import android.graphics.Bitmap
import androidx.compose.runtime.MutableState

interface ImageRepository {
    suspend fun saveImage(image: Image): Boolean
    suspend fun loadImage(name: String): MutableState<Bitmap?>
    suspend fun loadAllImage(): List<Image>
}