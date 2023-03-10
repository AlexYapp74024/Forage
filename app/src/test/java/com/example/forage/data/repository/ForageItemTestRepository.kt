package com.example.forage.data.repository

import androidx.compose.runtime.mutableStateListOf
import com.example.forage.feature_forage.domain.model.Category
import com.example.forage.feature_forage.domain.model.ForageItem
import com.example.forage.feature_forage.domain.model.relations.CategoryWithForageItems
import com.example.forage.feature_forage.domain.repository.ForageItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class ForageItemTestRepository : ForageItemRepository {

    private var itemList = mutableStateListOf<ForageItem>()
    private var categoryList = mutableStateListOf<Category>()

    override suspend fun insertItem(item: ForageItem): Long {
        itemList.add(item)
        return item.id.toLong()
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

    override suspend fun insertCategory(category: Category): Long {
        categoryList.add(category)
        return category.id.toLong()
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

    override suspend fun getCategoryWithItems(): List<CategoryWithForageItems> {
        return categoryList.map { category ->
            val items = itemList.filter { it.categoryID == category.id }
            CategoryWithForageItems(category, items)
        }
    }
}


