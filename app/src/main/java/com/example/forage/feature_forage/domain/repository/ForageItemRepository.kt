package com.example.forage.feature_forage.domain.repository

import com.example.forage.feature_forage.domain.model.ForageItem
import kotlinx.coroutines.flow.Flow

interface ForageItemRepository {

    suspend fun insert(item: ForageItem)

    suspend fun delete(item: ForageItem)

    fun getItem(id: Int): Flow<ForageItem?>

    fun getAll(): Flow<List<ForageItem>>
}