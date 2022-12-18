package com.example.forage.feature_forage.domain.model

import android.graphics.Bitmap
import com.example.forage.core.image_processing.EntityWithImage

data class ForageItemWithImage(
    val item: ForageItem,
    override val bitmap: Bitmap? = null
) : EntityWithImage() {
    override val imagePath
        get() = "${item.id}.png"
}

