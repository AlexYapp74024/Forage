package com.example.forage.feature_forage.presentation.item_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.forage.feature_forage.domain.use_case.ForageItemUseCases
import com.example.forage.feature_forage.domain.util.ForageItemOrder
import com.example.forage.feature_forage.domain.util.OrderType
import com.example.forage.feature_forage.presentation.destinations.ForageItemDetailScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ForageItemListViewModel @Inject constructor(
    private val useCases: ForageItemUseCases
) : ViewModel() {

    private val _state = mutableStateOf(ItemsListState())
    val state: State<ItemsListState> = _state

    init {
        getItems(ForageItemOrder.Name(OrderType.Descending))
    }

    fun viewItem(
        navigator: DestinationsNavigator,
        id: Int
    ) {
        navigator.navigate(ForageItemDetailScreenDestination(id)) 
    }

    private var getItemsJob: Job? = null

    private fun getItems(forageItemOrder: ForageItemOrder) {
        getItemsJob?.cancel()
        getItemsJob =
            useCases.getAllForageItems(
                itemOrder = forageItemOrder,
                onlyInSeason = state.value.displayOnlyInSeason
            ).onEach { items ->
                _state.value = _state.value.copy(
                    items = items,
                    itemOrder = forageItemOrder
                )
            }.launchIn(viewModelScope)
    }
}