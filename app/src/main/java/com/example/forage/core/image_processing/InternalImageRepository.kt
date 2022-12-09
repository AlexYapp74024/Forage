package com.example.forage.core.image_processing

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class InternalImageRepository(private val context: Context) : ImageRepository {

    override suspend fun saveImage(image: Image): Boolean {
        return withContext(Dispatchers.IO) {
            context.openFileOutput(image.name, Context.MODE_PRIVATE).use { stream ->
                image.bitmap.compress(Bitmap.CompressFormat.PNG, 95, stream)
            }
        }
    }

    override suspend fun loadImage(
        name: String,
        onImageRecieved: (Bitmap) -> Unit
    ) {
        return withContext(Dispatchers.IO) {
            val file = File("${context.filesDir.absolutePath}/$name")
            val imageUri: Uri = Uri.fromFile(file)

            Glide.with(context)
                .asBitmap()
                .load(imageUri)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onLoadCleared(placeholder: Drawable?) {}
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        onImageRecieved(resource)
                    }
                })
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