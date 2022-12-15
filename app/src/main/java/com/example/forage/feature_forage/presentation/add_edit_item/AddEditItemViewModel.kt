package com.example.forage.feature_forage.presentation.add_edit_item

import android.graphics.Bitmap
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.forage.feature_forage.domain.model.ForageItem
import com.example.forage.feature_forage.domain.model.ForageItemWithImage
import com.example.forage.feature_forage.domain.use_case.ForageItem_UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditItemViewModel @Inject constructor(
    private val useCases: ForageItem_UseCases,
) : ViewModel() {

    private var _item = mutableStateOf(ForageItemWithImage(ForageItem()))
    val item: State<ForageItemWithImage> = _item

    fun updateItemBitmap(bitmap: Bitmap) {
        _item.value = _item.value.copy(bitmap = bitmap)
    }

    fun updateItemState(item: ForageItem) {
        _item.value = _item.value.copy(item = item)
    }

    fun retrieveItem(id: Int) {
        viewModelScope.launch {
            useCases.getForageItem.withImage(id) {
                _item.value = it
            }
        }
    }

    fun addItem() {
        viewModelScope.launch {
            useCases.addForageItem(item.value)
        }
    }

    fun deleteItem() {
        viewModelScope.launch {
            _item.value.run {
                useCases.deleteForageItem(item)
            }
        }
    }
}