package com.example.forage.feature_forage.domain.use_case

import android.content.Context
import android.graphics.Bitmap
import com.example.forage.core.image_processing.Image
import com.example.forage.core.image_processing.InternalImageRepository
import com.example.forage.feature_forage.domain.model.ForageItem
import com.example.forage.feature_forage.domain.model.imagePath
import com.example.forage.feature_forage.domain.repository.ForageItemRepository

class AddForageItem(
    private val repository: ForageItemRepository
) {
    suspend operator fun invoke(forageItem: ForageItem, bitmap: Bitmap?, context: Context) {
        repository.insert(forageItem)
        bitmap?.let {
            InternalImageRepository(context).saveImage(
                Image(forageItem.imagePath, it)
            )
        }
    }
}