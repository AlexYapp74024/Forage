package com.example.forage.feature_forage.presentation.add_edit_item

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.forage.feature_forage.domain.model.ForageItem
import com.example.forage.feature_forage.domain.model.ForageItemWithImage
import com.example.forage.feature_forage.domain.use_case.ForageItemUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditItemViewModel @Inject constructor(
    private val useCases: ForageItemUseCases,
) : ViewModel() {

    private var _itemState = mutableStateOf(ForageItemWithImage(ForageItem()))
    val itemState: State<ForageItemWithImage> = _itemState

    fun updateItemBitmap(bitmap: Bitmap) {
        _itemState.value = _itemState.value.copy(bitmap = bitmap)
    }

    fun updateItemState(item: ForageItem) {
        _itemState.value = _itemState.value.copy(item = item)
    }

    fun retrieveItem(id: Int, context: Context) {
        viewModelScope.launch {
            useCases.getForageItem(id)
                .map { it ?: ForageItem() }
                .collect { item ->
                    _itemState.value = ForageItemWithImage(item)
                    _itemState.value.loadImage(context) {
                        updateItemBitmap(it)
                    }
                }
        }
    }

    fun addItem(context: Context) {
        viewModelScope.launch {
            _itemState.value.run {
                useCases.addForageItem(item)
                saveImage(context)
            }
        }
    }

    fun deleteItem() {
        viewModelScope.launch {
            _itemState.value.run {
                useCases.deleteForageItem(item)
            }
        }
    }
}