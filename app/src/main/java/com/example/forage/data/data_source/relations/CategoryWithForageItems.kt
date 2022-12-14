package com.example.forage.data.data_source.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.forage.feature_forage.domain.model.Category
import com.example.forage.feature_forage.domain.model.ForageItem

data class CategoryWithForageItems(
    @Embedded val category: Category,
    @Relation(
        parentColumn = "id",
        entityColumn = "categoryID"
    )
    val forageItems: List<ForageItem>
)
