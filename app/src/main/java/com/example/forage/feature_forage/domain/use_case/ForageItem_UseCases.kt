package com.example.forage.feature_forage.domain.use_case

import com.example.forage.core.image_processing.ImageRepository
import com.example.forage.feature_forage.domain.repository.ForageItemRepository

@Suppress("ClassName")
data class ForageItem_UseCases(
    val getAllForageItems: GetAllForageItems,
    val getForageItem: GetForageItem,
    val deleteForageItem: DeleteForageItem,
    val addForageItem: AddForageItem,
    val getAllCategories: GetAllCategories,
    val getCategoryWithItems: GetCategoryWithItems,
    val addCategory: AddCategory,
    val deleteCategory: DeleteCategory,
) {
    companion object {
        fun instantiate(
            repository: ForageItemRepository,
            imageRepository: ImageRepository,
        ) =
            ForageItem_UseCases(
                getAllForageItems = GetAllForageItems(repository, imageRepository),
                getForageItem = GetForageItem(repository, imageRepository),
                deleteForageItem = DeleteForageItem(repository),
                addForageItem = AddForageItem(repository, imageRepository),
                getAllCategories = GetAllCategories(repository),
                getCategoryWithItems = GetCategoryWithItems(repository),
                addCategory = AddCategory(repository),
                deleteCategory = DeleteCategory(repository),
            )
    }
}
