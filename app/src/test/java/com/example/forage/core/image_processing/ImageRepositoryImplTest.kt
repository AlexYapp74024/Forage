package com.example.forage.core.image_processing

import android.graphics.Bitmap
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class TestImageRepository : ImageRepository {

    val bitmaps = mutableMapOf<String, Bitmap>()

    override suspend fun saveImage(name: String, bitmap: Bitmap): Boolean {
        bitmaps[name] = bitmap
        return true
    }

    override suspend fun loadImage(name: String): Flow<Bitmap?> {
        return flowOf(bitmaps[name])
    }

}