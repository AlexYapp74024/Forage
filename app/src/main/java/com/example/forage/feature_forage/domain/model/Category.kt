package com.example.forage.feature_forage.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Category(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",
) : Parcelable

val exampleCategory = Category(1, "Berries")
