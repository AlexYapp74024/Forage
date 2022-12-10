package com.example.forage.feature_forage.domain.model

import android.content.Context
import android.graphics.Bitmap
import com.example.forage.core.image_processing.InternalImageRepository

data class ForageItemWithImage(
    val item: ForageItem,
    val bitmap: Bitmap? = null,
//    private var _bitmap: MutableState<Bitmap?> = mutableStateOf(null)
) {
//    val bitmap: State<Bitmap?> = _bitmap,

    //    fun updateBitmap(bitmap: Bitmap) {
//        _bitmap.value = bitmap
//    }
    suspend fun loadImage(context: Context, onImageReceived: (Bitmap) -> Unit) {
        InternalImageRepository(context).loadImage(item.imagePath) { bitmap ->
            onImageReceived(bitmap)
        }
    }

    suspend fun saveImage(context: Context) {
        bitmap?.let {
            InternalImageRepository(context).saveImage(item.imagePath, it)
        }
    }
}