package com.example.forage.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.forage.feature_forage.domain.model.Category
import com.example.forage.feature_forage.domain.model.ForageItem

@Database(
    entities = [ForageItem::class, Category::class],
    version = 2,
)
abstract class ForageItemDatabase : RoomDatabase() {

    abstract val dao: ForageItemDao

    companion object {
        val migration1to2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS `Category` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL)")
                database.execSQL("ALTER TABLE `ForageItem` ADD COLUMN `categoryID` INT")
            }
        }

        const val DATABASE_NAME = "ForageDb"
    }
}