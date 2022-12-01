package com.example.forage.feature_forage.presentation.items

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.forage.feature_forage.domain.use_case.ForageItemUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForageItemViewModel @Inject constructor(
    val forageItemUseCases: ForageItemUseCases
) : ViewModel() {

    private val _state = mutableStateOf(ItemsState())
    val state: State<ItemsState> = _state
}