package com.example.forage.feature_forage.presentation.add_edit_item

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.forage.feature_forage.domain.model.ForageItem
import com.example.forage.feature_forage.domain.use_case.ForageItemUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditItemViewModel @Inject constructor(
    private val useCases: ForageItemUseCases,
) : ViewModel() {

    private var _state = mutableStateOf(AddEditItemUiState())
    val state: State<AddEditItemUiState> = _state

    fun updateItemBitmap(bitmap: Bitmap) {
        _state.value = _state.value.copy(bitmap = bitmap)
    }

    fun updateItemState(item: ForageItem) {
        _state.value = _state.value.copy(item = item)
    }

    fun retrieveItem(id: Int) {
        viewModelScope.launch {
            useCases.getForageItem(id)
                .map { it ?: ForageItem() }
                .collect { item ->
                    _state.value = _state.value.copy(item = item)
                }
        }
    }

    fun addItem(context: Context) {
        viewModelScope.launch {
            _state.value.run {
                useCases.addForageItem(item, bitmap, context)
            }
        }
    }

    fun deleteItem() {
        viewModelScope.launch {
            _state.value.run {
                useCases.deleteForageItem(item)
            }
        }
    }
}