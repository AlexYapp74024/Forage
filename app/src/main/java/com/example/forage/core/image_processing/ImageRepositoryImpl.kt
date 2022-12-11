package com.example.forage.core.image_processing

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class ImageRepositoryImpl(val context: Context) : ImageRepository {

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
            val imageUri: Uri = Uri.fromFile(file)

            context.contentResolver.openInputStream(imageUri).use { stream ->
                onImageReceived(BitmapFactory.decodeStream(stream))
            }
        }
    }
}