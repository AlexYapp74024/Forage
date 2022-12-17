package com.example.forage.core.image_processing

import android.graphics.Bitmap
import kotlinx.coroutines.flow.Flow

interface ImageRepository {
    suspend fun saveImage(name: String, bitmap: Bitmap): Boolean
    suspend fun loadImage(name: String): Flow<Bitmap?>
}