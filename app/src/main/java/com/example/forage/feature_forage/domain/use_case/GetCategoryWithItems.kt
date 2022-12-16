package com.example.forage.feature_forage.domain.use_case

import com.example.forage.feature_forage.domain.model.Category
import com.example.forage.feature_forage.domain.repository.ForageItemRepository

class GetCategoryWithItems(
    private val repository: ForageItemRepository,
) {
    suspend operator fun invoke(category: Category) = repository.getCategoryWithItems(category)
}