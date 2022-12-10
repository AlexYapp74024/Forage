package com.example.forage.feature_forage.presentation.item_list

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.forage.feature_forage.domain.model.ForageItemWithImage
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

    fun viewItem(
        navigator: DestinationsNavigator, id: Int
    ) {
        navigator.navigate(ForageItemDetailScreenDestination(id))
    }

    private var getItemsJob: Job? = null

    fun retrieveItems(context: Context) {
        getItemsJob?.cancel()
        getItemsJob = viewModelScope.launch {
            useCases.getAllForageItems(
                itemOrder = state.value.itemOrder, onlyInSeason = state.value.displayOnlyInSeason
            ).collect { items ->
                items.map { item ->
                    ForageItemWithImage(item)
                }.map { imageItem ->
//                    imageItem.loadImage(context) { bitmap ->
//                        println("Updated Images")
////                        imageItem.updateBitmap(bitmap)
//                    }
                    imageItem
                }.run {
                    _state.value = _state.value.copy(items = this)
                }
            }
        }
    }
}