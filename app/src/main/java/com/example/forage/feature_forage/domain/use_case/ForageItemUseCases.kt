package com.example.forage.feature_forage.domain.use_case

data class ForageItemUseCases(
    val getAllForageItems: GetAllForageItems,
    val getForageItem: GetForageItem,
    val deleteForageItem: DeleteForageItem,
    val addForageItem: AddForageItem,
)
