package com.example.forage.feature_forage.domain.repository

import com.example.forage.feature_forage.domain.model.Category
import com.example.forage.feature_forage.domain.model.ForageItem
import com.example.forage.feature_forage.domain.model.relations.CategoryWithForageItems
import kotlinx.coroutines.flow.Flow

interface ForageItemRepository {

    suspend fun insertItem(item: ForageItem): Long
    suspend fun deleteItem(item: ForageItem)
    fun getItem(id: Int): Flow<ForageItem?>
    fun getAllItem(): Flow<List<ForageItem>>

    suspend fun insertCategory(category: Category): Long
    suspend fun deleteCategory(category: Category)
    fun getCategory(id: Int): Flow<Category?>
    fun getAllCategory(): Flow<List<Category>>

    suspend fun getCategoryWithItems(category: Category): List<CategoryWithForageItems>
    suspend fun getCategoryWithItems(): List<CategoryWithForageItems>
}