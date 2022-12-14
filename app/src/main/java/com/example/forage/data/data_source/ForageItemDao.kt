package com.example.forage.data.data_source

import androidx.room.*
import com.example.forage.data.data_source.relations.CategoryWithForageItems
import com.example.forage.feature_forage.domain.model.Category
import com.example.forage.feature_forage.domain.model.ForageItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ForageItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: ForageItem)

    @Delete
    suspend fun deleteItem(item: ForageItem)

    @Query("SELECT * FROM ForageItem WHERE id = :id")
    fun getItem(id: Int): Flow<ForageItem?>

    @Query("SELECT * FROM ForageItem")
    fun getAllItem(): Flow<List<ForageItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: Category)

    @Delete
    suspend fun deleteCategory(category: Category)

    @Query("SELECT * FROM Category WHERE id = :id")
    fun getCategory(id: Int): Flow<Category?>

    @Query("SELECT * FROM Category")
    fun getAllCategory(): Flow<List<Category>>

    @Transaction
    @Query("SELECT * FROM Category WHERE id = :categoryID")
    suspend fun getCategoryWithItems(categoryID: Int): List<CategoryWithForageItems>
}