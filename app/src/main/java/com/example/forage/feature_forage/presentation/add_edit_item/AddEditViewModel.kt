package com.example.forage.feature_forage.presentation.add_edit_item

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
class AddEditViewModel @Inject constructor(
    private val useCases: ForageItemUseCases,
) : ViewModel() {

    private var _item = mutableStateOf(ForageItem())
    val item: State<ForageItem> = _item

    fun updateItemState(item: ForageItem) {
        _item.value = item
    }

    fun retrieveItem(id: Int) {
        viewModelScope.launch {
            useCases.getForageItem(id)
                .map { it ?: ForageItem() }
                .collect { _item.value = it }
        }
    }

    fun addItem() {
        viewModelScope.launch {
            useCases.addForageItem(item.value)
        }
    }

    fun deleteItem() {
        viewModelScope.launch {
            useCases.deleteForageItem(item.value)
        }
    }
}