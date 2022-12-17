package com.example.forage.feature_forage.presentation.item_list

import android.graphics.Bitmap
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.forage.core.ui_util.BitmapWithDefault
import com.example.forage.core.ui_util.ForageTopAppBar
import com.example.forage.feature_forage.domain.model.ForageItem
import com.example.forage.feature_forage.presentation.destinations.AddForageItemScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

private lateinit var viewModel: ForageItemListViewModel
private var navigator: DestinationsNavigator = EmptyDestinationsNavigator

@RootNavGraph(start = true)
@Destination
@Composable
fun ForageItemListScreen(
    navigatorIn: DestinationsNavigator,
    viewModelIn: ForageItemListViewModel = hiltViewModel(),
) {
    viewModel = viewModelIn
    navigator = navigatorIn

    viewModel.retrieveItems()
    ForageItemListScreen()
}

@Composable
fun ForageItemListScreen() {
    val bitmaps by viewModel.bitmaps
    ForageItemListScreenContent(bitmaps)
}

@Preview(showBackground = true)
@Composable
fun ForageItemListPreview() {
    ForageItemListScreenContent(
        forageItemList = mapOf(
            ForageItem(
                id = 1,
                name = "wild gooseberry",
                location = "Mountain View",
                inSeason = true,
                notes = ""
            ) to flow { emit(null) },
            ForageItem(
                id = 2,
                name = "Blackberry",
                location = "Forest",
                inSeason = true,
                notes = ""
            ) to flow { emit(null) }
        )
    )
}

@Composable
fun ForageItemListScreenContent(
    forageItemList: Map<ForageItem, Flow<Bitmap?>>,
    modifier: Modifier = Modifier,
) {
    println("Content Recomposition")
    Scaffold(modifier = modifier, floatingActionButton = {
        AddItemFloatingActionButton(floatingActionBtnOnClick = {
            navigator.navigate(AddForageItemScreenDestination)
        })
    }, topBar = {
        ForageTopAppBar(
            Title = "Items",
            canNavigateBack = false,
            navigateUp = { },
        )
    }) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            forageItemList.onEach { (item, bitmapFlow) ->
                item {
                    ForageListItemEntry(
                        item = item,
                        bitmapFlow = bitmapFlow,
                        itemOnClick = {
                            viewModel.viewItem(navigator, item.id)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ForageListItemEntry(
    item: ForageItem,
    bitmapFlow: Flow<Bitmap?>,
    itemOnClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier
        .fillMaxWidth()
        .height(72.dp)
        .clickable {
            itemOnClick()
        }) {

        val bitmap by bitmapFlow.collectAsState(initial = null)
        BitmapWithDefault(
            bitmap = bitmap,
            contentDescription = null,
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
                .padding(4.dp),
            contentScaleIfNotNull = ContentScale.Fit,
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = item.name, style = MaterialTheme.typography.h5)
            Text(text = item.location, style = MaterialTheme.typography.body1)
        }
    }
}

@Composable
fun AddItemFloatingActionButton(
    floatingActionBtnOnClick: () -> Unit
) {
    FloatingActionButton(
        onClick = floatingActionBtnOnClick,
        backgroundColor = MaterialTheme.colors.surface,
        contentColor = MaterialTheme.colors.onSurface
    ) {
        Icon(
            Icons.Filled.Add, contentDescription = "Add Item"
        )
    }
}