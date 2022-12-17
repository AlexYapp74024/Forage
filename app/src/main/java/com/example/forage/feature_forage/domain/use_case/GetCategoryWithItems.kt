package com.example.forage.feature_forage.domain.use_case

import com.example.forage.feature_forage.domain.model.Category
import com.example.forage.feature_forage.domain.model.ForageItem
import com.example.forage.feature_forage.domain.repository.ForageItemRepository

class GetCategoryWithItems(
    private val repository: ForageItemRepository,
) {
    suspend operator fun invoke(category: Category) =
        mutableMapOf<Category, List<ForageItem>>().also { map ->
            repository.getCategoryWithItems(category).map {
                map[it.category] = it.forageItems
            }
        }
}