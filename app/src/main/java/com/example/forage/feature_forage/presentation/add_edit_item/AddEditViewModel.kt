package com.example.forage.feature_forage.presentation.add_edit_item

import androidx.lifecycle.ViewModel
import com.example.forage.feature_forage.domain.use_case.ForageItemUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val forageItemUseCases: ForageItemUseCases
) : ViewModel() {


}