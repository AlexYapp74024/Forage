package com.example.forage.data.repository

import com.example.forage.data.data_source.ForageItemDao
import com.example.forage.feature_forage.domain.model.Category
import com.example.forage.feature_forage.domain.model.ForageItem
import com.example.forage.feature_forage.domain.repository.ForageItemRepository
import kotlinx.coroutines.flow.Flow

class ForageItemRepositoryImpl(
    private val dao: ForageItemDao
) : ForageItemRepository {
    override suspend fun insertItem(item: ForageItem) = dao.insertItem(item)

    override suspend fun deleteItem(item: ForageItem) = dao.deleteItem(item)

    override fun getItem(id: Int): Flow<ForageItem?> = dao.getItem(id)

    override fun getAllItem(): Flow<List<ForageItem>> = dao.getAllItem()

    override suspend fun insertCategory(item: Category) = dao.insertCategory(item)

    override suspend fun deleteCategory(item: Category) = dao.deleteCategory(item)

    override fun getCategory(id: Int): Flow<Category?> = dao.getCategory(id)

    override fun getAllCategory(): Flow<List<Category>> = dao.getAllCategory()

    override suspend fun getCategoryWithItems(category: Category) =
        dao.getCategoryWithItems(category.id)
}