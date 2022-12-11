package com.example.forage.core.image_processing

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream

class InternalImageRepository(private val context: Context) : ImageRepository {

    override suspend fun saveImage(name: String, bitmap: Bitmap): Boolean {
        return withContext(Dispatchers.IO) {
            context.openFileOutput(name, Context.MODE_PRIVATE).use { stream ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 95, stream)
            }
        }
    }

    override suspend fun loadImage(
        name: String,
        onImageReceived: (Bitmap) -> Unit
    ) {
        return withContext(Dispatchers.IO) {
            val file = File("${context.filesDir.absolutePath}/$name")

            FileInputStream(file).use { stream ->
                onImageReceived(BitmapFactory.decodeStream(stream))
            }
        }
    }
}