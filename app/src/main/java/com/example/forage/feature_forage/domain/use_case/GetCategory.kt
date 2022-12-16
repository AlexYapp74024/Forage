package com.example.forage.feature_forage.domain.use_case

import com.example.forage.feature_forage.domain.repository.ForageItemRepository

class GetCategory(
    private val repository: ForageItemRepository
) {
    fun invoke(id: Int) = repository.getCategory(id)
}