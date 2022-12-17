package com.example.forage.feature_forage.presentation.item_list

import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.forage.core.asState
import com.example.forage.feature_forage.domain.model.ForageItem
import com.example.forage.feature_forage.domain.use_case.ForageItem_UseCases
import com.example.forage.feature_forage.presentation.destinations.ForageItemDetailScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForageItemListViewModel @Inject constructor(
    private val useCases: ForageItem_UseCases
) : ViewModel() {

    private var _state = mutableStateOf(ItemsListState())
    val state = _state.asState()

    private var _bitmaps = mutableStateOf<Map<ForageItem, Flow<Bitmap?>>>(mapOf())
    val bitmaps = _bitmaps.asState()

    fun viewItem(
        navigator: DestinationsNavigator, id: Int
    ) {
        navigator.navigate(ForageItemDetailScreenDestination(id))
    }

    private var getItemsJob: Job? = null

    fun retrieveItems() {
        getItemsJob?.cancel()
        getItemsJob = viewModelScope.launch {
            useCases.getAllForageItems.withImages(
                itemOrder = state.value.itemOrder,
                onlyInSeason = state.value.displayOnlyInSeason
            ).collect {
                _bitmaps.value = it
            }
        }
    }
}
