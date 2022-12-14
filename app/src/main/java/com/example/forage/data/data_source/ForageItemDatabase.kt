package com.example.forage.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.forage.feature_forage.domain.model.Category
import com.example.forage.feature_forage.domain.model.ForageItem

@Database(
    entities = [ForageItem::class, Category::class],
    version = 1,
)
abstract class ForageItemDatabase : RoomDatabase() {

    abstract val dao: ForageItemDao

    companion object {
        const val DATABASE_NAME = "ForageDb"
    }
}