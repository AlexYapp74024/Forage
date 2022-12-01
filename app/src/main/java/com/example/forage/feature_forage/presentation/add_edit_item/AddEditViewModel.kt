package com.example.forage.feature_forage.presentation.add_edit_item

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

    fun retrieveItem(id: Int) = useCases.getForageItem(id).map { it ?: ForageItem() }

    fun addItem(item: ForageItem) {
        viewModelScope.launch {
            useCases.addForageItem(item)
        }
    }

    fun deleteItem(item: ForageItem) {
        viewModelScope.launch {
            useCases.deleteForageItem(item)
        }
    }
}