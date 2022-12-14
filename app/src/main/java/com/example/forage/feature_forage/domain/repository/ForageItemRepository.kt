package com.example.forage.feature_forage.domain.repository

import com.example.forage.data.data_source.relations.CategoryWithForageItems
import com.example.forage.feature_forage.domain.model.Category
import com.example.forage.feature_forage.domain.model.ForageItem
import kotlinx.coroutines.flow.Flow

interface ForageItemRepository {

    suspend fun insertItem(item: ForageItem)
    suspend fun deleteItem(item: ForageItem)
    fun getItem(id: Int): Flow<ForageItem?>
    fun getAllItem(): Flow<List<ForageItem>>

    suspend fun insertCategory(item: Category)
    suspend fun deleteCategory(item: Category)
    fun getCategory(id: Int): Flow<Category?>
    fun getAllCategory(): Flow<List<Category>>

    suspend fun getCategoryWithItems(category: Category): List<CategoryWithForageItems>
}