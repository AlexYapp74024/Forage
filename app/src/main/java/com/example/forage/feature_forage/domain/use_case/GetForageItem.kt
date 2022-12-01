package com.example.forage.feature_forage.domain.use_case

import com.example.forage.feature_forage.domain.repository.ForageItemRepository

class GetForageItem(
    private val repository: ForageItemRepository
) {
    operator fun invoke(id: Int) = repository.getItem(id)
}