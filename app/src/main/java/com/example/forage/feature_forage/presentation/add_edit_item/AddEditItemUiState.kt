package com.example.forage.feature_forage.presentation.add_edit_item

import android.graphics.Bitmap
import com.example.forage.feature_forage.domain.model.ForageItem

data class AddEditItemUiState(
    val item: ForageItem = ForageItem(),
    val bitmap: Bitmap? = null,
)