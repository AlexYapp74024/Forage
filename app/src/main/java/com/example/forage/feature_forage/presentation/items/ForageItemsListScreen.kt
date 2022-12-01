package com.example.forage.feature_forage.presentation.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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

@RootNavGraph(start = true)
@Destination
@Composable
fun ForageItemListScreen(
    viewModel: ForageItemViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
) {
    val state = viewModel.state.value
    ForageItemListScreenContent(state.items, navigator)
}

@Preview(showBackground = true)
@Composable
fun ForageItemListPreview() {
    ForageItemListScreenContent(
        forageItemList = listOf(
            ForageItem(0, "wild gooseberry", "Mountain View", true, ""),
            ForageItem(2, "Blackberry (Rubus sp.)", "Forest", true, "")
        ),
        EmptyDestinationsNavigator
    )
}

@Composable
fun ForageItemListScreenContent(
    forageItemList: List<ForageItem>,
    navigator: DestinationsNavigator,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            ItemListFloatingActionButton(floatingActionBtnOnClick = {
                navigator.navigate(AddForageItemScreenDestination(0))
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
                    itemOnClick = { })
            }

        }
    }
}

@Composable
fun ForageListItemEntry(
    item: ForageItem,
    itemOnClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.clickable {
        itemOnClick(item.id)
    }) {
        Text(text = item.name, style = MaterialTheme.typography.h5)
        Text(text = item.location, style = MaterialTheme.typography.body1)
    }
}

@Composable
fun ItemListFloatingActionButton(
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