package com.example.forage.feature_forage.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ForageItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",
    val categoryID: Int? = null,
    val location: String = "",
    val inSeason: Boolean = true,
    val notes: String = "",
)

val exampleForageItem: ForageItem
    get() = ForageItem(
        id = 1,
        categoryID = exampleCategory.id,
        name = "Honeyberry",
        location = "CertShop",
        notes = "cute af"
    )