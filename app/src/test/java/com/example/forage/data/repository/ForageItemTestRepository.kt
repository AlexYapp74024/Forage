package com.example.forage.data.repository

import androidx.compose.runtime.mutableStateListOf
import com.example.forage.data.data_source.relations.CategoryWithForageItems
import com.example.forage.feature_forage.domain.model.Category
import com.example.forage.feature_forage.domain.model.ForageItem
import com.example.forage.feature_forage.domain.repository.ForageItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class ForageItemTestRepository : ForageItemRepository {

    private var itemList = mutableStateListOf<ForageItem>()
    private var categoryList = mutableStateListOf<Category>()

    override suspend fun insertItem(item: ForageItem) {
        itemList.add(item)
    }

    override suspend fun deleteItem(item: ForageItem) {
        itemList.remove(item)
    }

    override fun getItem(id: Int): Flow<ForageItem?> = flow {
        emit(itemList.find {
            it.id == id
        })
    }


    override fun getAllItem(): Flow<List<ForageItem>> = flow {
        emit(itemList)
    }

    override suspend fun insertCategory(category: Category) {
        categoryList.add(category)
    }

    override suspend fun deleteCategory(category: Category) {
        categoryList.remove(category)
    }

    override fun getCategory(id: Int): Flow<Category?> {
        return flowOf(categoryList.find {
            it.id == id
        })
    }

    override fun getAllCategory(): Flow<List<Category>> {
        return flowOf(categoryList)
    }

    override suspend fun getCategoryWithItems(category: Category): List<CategoryWithForageItems> {
        val items = itemList.filter { it.categoryID == category.id }
        return listOf(
            CategoryWithForageItems(category = category, forageItems = items)
        )
    }
}


