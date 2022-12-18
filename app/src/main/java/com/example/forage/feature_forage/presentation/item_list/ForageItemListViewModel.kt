package com.example.forage.feature_forage.presentation.item_list

import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.forage.core.asState
import com.example.forage.feature_forage.domain.model.Category
import com.example.forage.feature_forage.domain.model.ForageItem
import com.example.forage.feature_forage.domain.use_case.ForageItem_UseCases
import com.example.forage.feature_forage.presentation.destinations.ForageItemDetailScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForageItemListViewModel @Inject constructor(
    private val useCases: ForageItem_UseCases
) : ViewModel() {

    private var _state = mutableStateOf(ItemsListState())
    val state = _state.asState()

    private var categories = MutableStateFlow<Map<Category, List<ForageItem>>>(mapOf())
    private var bitmaps = MutableStateFlow<Map<ForageItem, Flow<Bitmap?>>>(mapOf())

    val items = combine(categories, bitmaps) { categories, bitmaps ->
        categories.map { (category, items) ->
            val itemAndBitmap = items.associateWith { bitmaps[it] ?: flowOf(null) }
            category to itemAndBitmap
        }.toMap().toMutableMap().also { map ->
            val itemWithNullCategory = bitmaps.filter { (item, _) -> item.categoryID == null }
            map[Category.noCategory] = itemWithNullCategory
        }.filter { (_, items) ->
            items.isNotEmpty()
        }
    }

    fun viewItem(
        navigator: DestinationsNavigator, id: Int
    ) {
        navigator.navigate(ForageItemDetailScreenDestination(id))
    }


    init {
        refreshItems()
    }

    private fun refreshItems() {
        getBitmap()
        getCategories()
    }

    private var getCategoryJob: Job? = null
    private fun getCategories() {
        getCategoryJob?.cancel()
        getCategoryJob = viewModelScope.launch {
            categories.value = useCases.getCategoryWithItems()
        }
    }

    private var getImageJob: Job? = null
    private fun getBitmap() {
        getImageJob?.cancel()
        getImageJob = viewModelScope.launch {
            useCases.getAllForageItems.withImages(
                itemOrder = state.value.itemOrder,
                onlyInSeason = state.value.displayOnlyInSeason,
                scope = this
            ).collect {
                bitmaps.value = it
            }
        }
    }
}
