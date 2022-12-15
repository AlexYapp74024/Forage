package com.example.forage.core.image_processing

import android.graphics.Bitmap

class TestImageRepository : ImageRepository {
    override suspend fun saveImage(name: String, bitmap: Bitmap): Boolean {
        return true
    }

    override suspend fun loadImage(name: String, onImageReceived: (Bitmap?) -> Unit) {
        onImageReceived(null)
    }

}