package com.example.forage.feature_forage.domain.use_case

import com.example.forage.feature_forage.domain.model.Category
import com.example.forage.feature_forage.domain.repository.ForageItemRepository

class DeleteCategory(
    private val repository: ForageItemRepository
) {
    suspend fun invoke(category: Category) {
        repository.deleteCategory(category)
    }
}