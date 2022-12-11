package com.example.forage.feature_forage.presentation.item_list

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.forage.feature_forage.domain.model.ForageItem
import com.example.forage.feature_forage.domain.use_case.ForageItemUseCases
import com.example.forage.feature_forage.presentation.destinations.ForageItemDetailScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForageItemListViewModel @Inject constructor(
    private val useCases: ForageItemUseCases
) : ViewModel() {

    private var _state = mutableStateOf(ItemsListState())
    val state: State<ItemsListState> = _state

    private var _bitmaps = mutableStateMapOf<ForageItem, Bitmap?>()
    val bitmaps: Map<ForageItem, Bitmap?> = _bitmaps

    fun viewItem(
        navigator: DestinationsNavigator, id: Int
    ) {
        navigator.navigate(ForageItemDetailScreenDestination(id))
    }

    private var getItemsJob: Job? = null

    fun retrieveItems(context: Context) {
        getItemsJob?.cancel()
        getItemsJob = viewModelScope.launch {
            useCases.getAllForageItems.withImages(
                context = context,
                itemOrder = state.value.itemOrder,
                onlyInSeason = state.value.displayOnlyInSeason
            ) { item, bmp ->
                _bitmaps[item] = bmp
            }
        }
    }
}
