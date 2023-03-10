package com.example.forage.feature_forage.domain.use_case

import com.example.forage.feature_forage.domain.model.ForageItem
import com.example.forage.feature_forage.domain.repository.ForageItemRepository

class DeleteForageItem(
    private val repository: ForageItemRepository
) {
    suspend operator fun invoke(forageItem: ForageItem) {
        repository.deleteItem(forageItem)
    }
}