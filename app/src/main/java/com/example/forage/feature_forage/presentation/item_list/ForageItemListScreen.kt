package com.example.forage.feature_forage.presentation.item_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.forage.feature_forage.domain.model.ForageItem
import com.example.forage.feature_forage.domain.model.ForageItemWithImage
import com.example.forage.feature_forage.presentation.destinations.AddForageItemScreenDestination
import com.example.forage.feature_forage.presentation.util.BitmapWithDefault
import com.example.forage.feature_forage.presentation.util.ForageTopAppBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

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

    viewModel.retrieveItems(LocalContext.current)
    println("Screen Recomposition")
    ForageItemListScreen()
}

@Composable
fun ForageItemListScreen() {
    val state by viewModel.state
    ForageItemListScreenContent(state.items)
}

@Preview(showBackground = true)
@Composable
fun ForageItemListPreview() {
    ForageItemListScreenContent(
        forageItemList = listOf(
            ForageItemWithImage(ForageItem(0, "wild gooseberry", "Mountain View", true, "")),
            ForageItemWithImage(ForageItem(2, "Blackberry", "Forest", true, "")),
        )
    )
}

@Composable
fun ForageItemListScreenContent(
    forageItemList: List<ForageItemWithImage>,
    modifier: Modifier = Modifier,
) {
    println("Content Recomposition")
    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            AddItemFloatingActionButton(floatingActionBtnOnClick = {
                navigator.navigate(AddForageItemScreenDestination)
            })
        },
        topBar = {
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
            items(forageItemList) { item ->
                ForageListItemEntry(item = item,
                    itemOnClick = {
                        viewModel.viewItem(navigator, item.item.id)
                    })
            }

        }
    }
}

@Composable
fun ForageListItemEntry(
    item: ForageItemWithImage,
    itemOnClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier.fillMaxWidth()) {
        val bitmap by item.bitmap

        BitmapWithDefault(
            bitmap = bitmap,
            contentDescription = null,
            modifier = Modifier
                .fillMaxHeight()
                .width(64.dp)
                .padding(4.dp),
            contentScaleIfNotNull = ContentScale.Fit,
        )

        Column(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                itemOnClick()
            }) {
            Text(text = item.item.name, style = MaterialTheme.typography.h5)
            Text(text = item.item.location, style = MaterialTheme.typography.body1)
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