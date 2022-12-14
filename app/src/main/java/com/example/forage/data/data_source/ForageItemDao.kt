package com.example.forage.data.data_source

import androidx.room.*
import com.example.forage.feature_forage.domain.model.ForageItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ForageItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ForageItem)

    @Delete
    suspend fun delete(item: ForageItem)

    @Query("SELECT * FROM ForageItem WHERE id = :id")
    fun getItem(id: Int): Flow<ForageItem?>

    @Query("SELECT * FROM ForageItem")
    fun getAll(): Flow<List<ForageItem>>
}