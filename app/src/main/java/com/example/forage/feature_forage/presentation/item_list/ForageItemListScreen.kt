package com.example.forage.feature_forage.presentation.item_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.forage.feature_forage.domain.model.ForageItem
import com.example.forage.feature_forage.presentation.destinations.AddForageItemScreenDestination
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
    viewModelIn: ForageItemListViewModel = hiltViewModel(),
    navigatorIn: DestinationsNavigator,
) {
    viewModel = viewModelIn
    navigator = navigatorIn

    val state = viewModel.state.value
    ForageItemListScreenContent(state.items)
}

@Preview(showBackground = true)
@Composable
fun ForageItemListPreview() {
    ForageItemListScreenContent(
        forageItemList = listOf(
            ForageItem(0, "wild gooseberry", "Mountain View", true, ""),
            ForageItem(2, "Blackberry (Rubus sp.)", "Forest", true, "")
        )
    )
}

@Composable
fun ForageItemListScreenContent(
    forageItemList: List<ForageItem>,
    modifier: Modifier = Modifier,
) {
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
                        viewModel.viewItem(navigator, item.id)
                    })
            }

        }
    }
}

@Composable
fun ForageListItemEntry(
    item: ForageItem,
    itemOnClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier
        .fillMaxWidth()
        .clickable {
            itemOnClick()
        }) {
        Text(text = item.name, style = MaterialTheme.typography.h5)
        Text(text = item.location, style = MaterialTheme.typography.body1)
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