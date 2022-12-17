package com.example.forage.feature_forage.domain.use_case

import com.example.forage.feature_forage.domain.model.Category
import com.example.forage.feature_forage.domain.model.ForageItem
import com.example.forage.feature_forage.domain.repository.ForageItemRepository
import kotlinx.coroutines.flow.map

class GetAllCategories(
    private val repository: ForageItemRepository,
) {
    operator fun invoke() = repository.getAllCategory().map { list ->
        list.sortedBy { it.name }
    }

    suspend fun withItems() = mutableMapOf<Category, List<ForageItem>>().also { map ->
        repository.getCategoryWithItems().map {
            map[it.category] = it.forageItems
        }
    }
}