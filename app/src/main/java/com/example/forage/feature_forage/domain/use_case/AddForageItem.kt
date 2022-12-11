package com.example.forage.feature_forage.domain.use_case

import android.content.Context
import com.example.forage.feature_forage.domain.model.ForageItem
import com.example.forage.feature_forage.domain.model.ForageItemWithImage
import com.example.forage.feature_forage.domain.repository.ForageItemRepository

class AddForageItem(
    private val repository: ForageItemRepository
) {
    suspend operator fun invoke(forageItem: ForageItem) {
        repository.insert(forageItem)
    }

    suspend operator fun invoke(context: Context, forageItem: ForageItemWithImage) {
        forageItem.run {
            this@AddForageItem(item)
            saveImage(context)
        }
    }
}