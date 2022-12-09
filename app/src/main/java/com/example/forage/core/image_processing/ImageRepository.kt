package com.example.forage.core.image_processing

import android.graphics.Bitmap

interface ImageRepository {
    suspend fun saveImage(image: Image): Boolean
    suspend fun loadImage(name: String, onImageRecieved: (Bitmap) -> Unit)
    suspend fun loadAllImage(): List<Image>
}