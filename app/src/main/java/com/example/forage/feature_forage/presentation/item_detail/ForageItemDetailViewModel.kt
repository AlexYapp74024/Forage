package com.example.forage.feature_forage.presentation.item_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.forage.feature_forage.domain.model.ForageItem
import com.example.forage.feature_forage.domain.model.ForageItemWithImage
import com.example.forage.feature_forage.domain.use_case.ForageItemUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForageItemDetailViewModel @Inject constructor(
    private val useCases: ForageItemUseCases,
) : ViewModel() {

    private var _item = mutableStateOf(ForageItemWithImage(ForageItem()))
    val item: State<ForageItemWithImage> = _item

    fun retrieveItem(id: Int) {
        viewModelScope.launch {
            useCases.getForageItem.withImage(id) {
                _item.value = it
            }
        }
    }
}