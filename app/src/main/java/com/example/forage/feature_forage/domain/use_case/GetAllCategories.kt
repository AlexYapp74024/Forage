package com.example.forage.feature_forage.domain.use_case

import com.example.forage.feature_forage.domain.repository.ForageItemRepository
import kotlinx.coroutines.flow.map

class GetAllCategories(
    private val repository: ForageItemRepository,
) {
    operator fun invoke() = repository.getAllCategory().map { list ->
        list.sortedBy { it.name }
    }

    fun withItems() = repository.getAllCategory().map { categoryList ->
        categoryList.map { category ->
            GetCategoryWithItems(repository).invoke(category)
        }
    }.map { list ->
        list.flatten().sortedBy { it.category.name }
    }
}