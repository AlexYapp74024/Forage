package com.example.forage.data.repository

import com.example.forage.data.data_source.ForageItemDao
import com.example.forage.feature_forage.domain.model.ForageItem
import com.example.forage.feature_forage.domain.repository.ForageItemRepository
import kotlinx.coroutines.flow.Flow

class ForageItemRepositoryImpl(
    private val dao: ForageItemDao
) : ForageItemRepository {
    override suspend fun insert(item: ForageItem) = dao.insert(item)

    override suspend fun delete(item: ForageItem) = dao.delete(item)

    override fun getItem(id: Int): Flow<ForageItem?> = dao.getItem(id)

    override fun getAll(): Flow<List<ForageItem>> = dao.getAll()
}