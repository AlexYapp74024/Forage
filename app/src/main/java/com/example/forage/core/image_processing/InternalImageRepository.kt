package com.example.forage.core.image_processing

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException

class InternalImageRepository(private val context: Context) : ImageRepository {

    override suspend fun saveImage(image: Image): Boolean {
        return withContext(Dispatchers.IO) {
            context.openFileOutput(image.name, Context.MODE_PRIVATE).use { stream ->
                image.bitmap.compress(Bitmap.CompressFormat.PNG, 95, stream)
            }
        }
    }

    override suspend fun loadImage(name: String): Image {
        return withContext(Dispatchers.IO) {
            val file = File("${context.filesDir.absolutePath}/$name")
            if (file.canRead() && file.isFile && file.path.endsWith(".png")) {
                val bytes = file.readBytes()
                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)

                Image(name, bitmap)
            } else {
                throw IOException("File not found")
            }
        }
    }

    override suspend fun loadAllImage(): List<Image> {
        return withContext(Dispatchers.IO) {
            val files = context.filesDir.listFiles()
            files?.filter { it.canRead() && it.isFile && it.path.endsWith(".png") }?.map { file ->
                val bytes = file.readBytes()
                val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)

                Image(file.name, bmp)
            } ?: listOf()
        }
    }
}